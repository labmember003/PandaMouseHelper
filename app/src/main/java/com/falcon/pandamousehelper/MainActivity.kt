package com.falcon.pandamousehelper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.pandamousehelper.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //val inputStream: InputStream = assets.open("inject.sh")
        /*
        // ROOT KI PERMISSION
        var p: Process
        val m: Process
        //Process m=null
        try {
            // Preform su to get root privledges
            p = Runtime.getRuntime().exec("su")



            // Attempt to write a file to a root-only
            val os = DataOutputStream(p.outputStream)
            os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n")

            // Close the terminal
            os.writeBytes("exit\n")
            os.flush()
            try {
                p.waitFor()
                if (p.exitValue() != 255) {
                    // TODO Code to run on success
                    //toastMessage("root")
                    Toast.makeText(this, "root", Toast.LENGTH_SHORT).show()
                    // TODO Code for running script
                    val inputStream: InputStream = assets.open("inject.sh")
                    //inputStream.read()
                    m = ProcessBuilder().command("PathToYourScript").start()
                } else {
                    // TODO Code to run on unsuccessful
                    Toast.makeText(this, "not", Toast.LENGTH_SHORT).show()
                    //toastMessage("not root")
                }
            } catch (e: InterruptedException) {
                // TODO Code to run in interrupted exception
                //toastMessage("not root")
            }
        } catch (e: IOException) {
            // TODO Code to run in input/output exception
            //toastMessage("not root")
        }
         */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}