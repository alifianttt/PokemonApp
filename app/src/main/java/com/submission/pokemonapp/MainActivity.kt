package com.submission.pokemonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.submission.pokemonapp.core.utils.toasShort
import com.submission.pokemonapp.databinding.ActivityMainBinding
import com.submission.pokemonapp.ui.home.ListPokemon

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var className = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            moveFragment(ListPokemon())
        }
        val packakeName = "com.submission.pokemonapp.favorite"
        className = "$packakeName.FavoritePokemon"
        binding.bottomNavMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    moveFragment(ListPokemon())
                    true
                }
                R.id.favorite -> {
                    var isInstall = false
                    try {
                        isInstall = true
                        val fragmentClass = Class.forName(className).newInstance() as Fragment
                        moveFragment(fragmentClass)
                    } catch (e: Exception){
                        isInstall = false
                        installFavorite()
                        toasShort("anda belum install")
                    }
                    isInstall
                }
                else -> {
                    false
                }
            }
        }

    }

    private fun moveFragment(fragment: Fragment){
       val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host,fragment)
        transaction.commit()
    }

    private fun installFavorite(){
        val moduleFavorite = "favorite"
        val splitInstallManagerFactory = SplitInstallManagerFactory.create(this)
        if (splitInstallManagerFactory.installedModules.contains(moduleFavorite)){
            val fragmentFavorite = Class.forName(className).newInstance() as Fragment
            moveFragment(fragmentFavorite)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManagerFactory.startInstall(request)
                .addOnSuccessListener {
                    val fragmentFavorite = Class.forName(className).newInstance() as Fragment
                    moveFragment(fragmentFavorite)
                }
                .addOnFailureListener {
                    toasShort("Error installing module")
                }
        }
    }
}