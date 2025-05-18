package com.example.namibiahockeysunion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val dbRef = FirebaseDatabase.getInstance().reference

    private val _loginResult = MutableStateFlow("")
    val loginResult: StateFlow<String> = _loginResult

    fun loginWithCredentials(email: String, password: String) {
        viewModelScope.launch {
            checkTeams(email, password)
        }
    }

    private fun checkTeams(email: String, password: String) {
        dbRef.child("teams").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (team in snapshot.children) {
                    val teamEmail = team.child("email").getValue(String::class.java)
                    val teamPass = team.child("password").getValue(String::class.java)
                    if (email == teamEmail && password == teamPass) {
                        _loginResult.value = "home_team"
                        return
                    }
                }
                checkPlayers(email, password)
            }

            override fun onCancelled(error: DatabaseError) {
                _loginResult.value = "invalid"
            }
        })
    }

    private fun checkPlayers(email: String, password: String) {
        val paths = listOf("PlayerRegistration/Male", "PlayerRegistration/Female")
        var found = false

        for (path in paths) {
            dbRef.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (player in snapshot.children) {
                        val playerEmail = player.child("email").getValue(String::class.java)
                        val playerPass = player.child("password").getValue(String::class.java)
                        if (email == playerEmail && password == playerPass) {
                            found = true
                            _loginResult.value = "home_player"
                            return
                        }
                    }
                    if (!found && path == "PlayerRegistration/Female") {
                        _loginResult.value = "invalid"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    _loginResult.value = "invalid"
                }
            })
        }
    }
    fun resetLoginResult() {
        _loginResult.value = ""
    }
}
