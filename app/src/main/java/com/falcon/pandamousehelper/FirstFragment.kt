package com.falcon.pandamousehelper

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.falcon.pandamousehelper.databinding.FragmentFirstBinding
import java.io.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    public fun showdialog() {
        var dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog2)
        var img1 = dialog.findViewById<ImageView>(R.id.dialog_imgview1)
        var text1 = dialog.findViewById<TextView>(R.id.dialog_textview1)
        var button1 = dialog.findViewById<Button>(R.id.dialog_button1)
        button1.setOnClickListener{
            Log.i("dialog", "mausi")
            //System.exit(0)
            activity!!.finish()
        }
        dialog.show()
        /*
        showdialog()
        write above line jaaha bhi dialog display krna hai
         */
    }

    fun onCreateView2(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        val directoryPath = context!!.getCacheDir()
        val file = File(directoryPath)
        if (file.isDirectory) {
            println("File is a Directory")
        } else {
            println("Directory doesn't exist!!")
        }
        */
        // ROOT KI PERMISSION
        var p: Process
        val m: Process
        try {
            // Preform su to get root privledges
            p = Runtime.getRuntime().exec("su")

            // Attempt to write a file to a root-only
            val os = DataOutputStream(p.outputStream)
            // os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n")

            // Close the terminal
            // os.writeBytes("exit\n")
            // os.flush()
            Log.i("mausi", "before everything")
            try {
                p.waitFor()
                if (p.exitValue() != 255) {
                    Log.i("mausi", "yes root")
                    // TODO Code to run on success
                    Toast.makeText(requireActivity(), "root",Toast.LENGTH_SHORT).show()
                    // TODO Code for running script
                    Log.i("mausi", "before directory creation")

                    // TEST
                    val testDirectory = File("/data/local/tmp", "scripts")
                    testDirectory.mkdir()
                    // TEST END
                    val scriptsDirectory = File(context?.cacheDir, "scripts")

                    if (scriptsDirectory.exists()) {
                        scriptsDirectory.deleteRecursively()
                    }
                    scriptsDirectory.mkdir()

                    Log.i("mausi", "${context?.cacheDir}")

                    Log.i("mausi", "directory created")
                    listOf("inject2.sh", "inject_wrapper.sh", "daemon.dex", "config.ini"
                        , "com.panda.mouselibinject.so", "com.panda.mouseinject.dex", "com.panda.gamepadlibinject.so"
                        , "com.panda.gamepadinject.dex").forEach() {
                        val inputStream: InputStream = context!!.assets!!.open("scripts/$it")
                        val file = File(scriptsDirectory, it)
                        file.createNewFile()
                        FileOutputStream(file).use { output ->
                            val buffer =
                                ByteArray(4 * 1024) // or other buffer size
                            var read: Int
                            while (inputStream.read(buffer).also { read = it } != -1) {
                                output.write(buffer, 0, read)
                            }
                            output.flush()
                            inputStream.close()
                        }
                    }
                    Log.i("mausi", "copied all files")

                    os.writeBytes("cp -r ${scriptsDirectory.absolutePath} /data/local/tmp && chmod +x /data/local/tmp/scripts/inject2.sh && sh /data/local/tmp/scripts/inject2.sh\n")
                    os.flush()
                    //p = ProcessBuilder().command(context?.cacheDir + "/scripts").start()
//                    GlobalScope.launch(Dispatchers.IO) {
//                        val s = Scanner(p.inputStream)
//                        while(true) {
//                            if (s.hasNextLine()) {
//                                Log.i("terminal", s.nextLine())
//                            } else {
//                                delay(1000)
//                            }
//                        }
//                    }
                    // m = ProcessBuilder().command("cp -r ${scriptsDirectory.absolutePath} /data/local/tmp && sh /data/local/tmp/scripts/inject2.sh").start()
                    Log.i("mausi", "script executed")
                    /*
                    //val inputStream: InputStream = context!!.assets!!.open("inject.sh")
                    val inputStream: InputStream = context!!.assets!!.open("scripts/inject2.sh")

                    val file = File(context!!.cacheDir, "inject2.sh")
                    FileOutputStream(file).use { output ->
                        val buffer =
                            ByteArray(4 * 1024) // or other buffer size
                        var read: Int
                        while (inputStream.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                        }
                        output.flush()
                        inputStream.close()
                    }
                    m = ProcessBuilder().command(file.absolutePath).start()
                    */
                } else {
                    // TODO Code to run on unsuccessful
                    Toast.makeText(requireActivity(), "not",Toast.LENGTH_SHORT).show()
                    //toastMessage("not root")
                }
            } catch (e: InterruptedException) {
                // TODO Code to run in interrupted exception
                Log.e("mausi", e.stackTraceToString())
            }
        } catch (e: IOException) {
            // TODO Code to run in input/output exception
            Log.e("mausi", e.stackTraceToString())
        }

        Toast.makeText(requireActivity(), "mausi",Toast.LENGTH_SHORT).show()
        //context!!.cacheDir
        Toast.makeText(requireActivity(), context!!.cacheDir.absolutePath,Toast.LENGTH_LONG).show()
        //getActionBar()!!.setTitle("mauso")
        //getActionBar()!!.title = "mausi"
        //Log.i("mausi", "${context.findFragmentById(R.id.secondFragment)}")
        //Log.i("fragmento", "${this.getId()}")
        getActionBar()!!.setDisplayShowTitleEnabled(true)
        getActionBar()!!.setTitle("mhf")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        //setHasOptionsMenu(true)
        return binding.root

    }



    private fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)!!.getSupportActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.mausiChips -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            if (!ExecuteAsRootBase.canRunRootCommands()) {
                showdialog()
            }
            ExecuteAsRootBase.canRunRootCommands()
            val testDirectory = File("/data/local/tmp", "scripts")
            testDirectory.mkdir()
            // TEST END
            val scriptsDirectory = File(context?.cacheDir, "scripts")

            if (scriptsDirectory.exists()) {
                scriptsDirectory.deleteRecursively()
            }
            scriptsDirectory.mkdir()

            Log.i("mausi", "${context?.cacheDir}")

            Log.i("mausi", "directory created")
            listOf("inject2.sh", "inject_wrapper.sh", "daemon.dex", "config.ini"
                , "com.panda.mouselibinject.so", "com.panda.mouseinject.dex", "com.panda.gamepadlibinject.so"
                , "com.panda.gamepadinject.dex").forEach() {
                val inputStream: InputStream = context!!.assets!!.open("scripts/$it")
                val file = File(scriptsDirectory, it)
                file.createNewFile()
                FileOutputStream(file).use { output ->
                    val buffer =
                        ByteArray(4 * 1024) // or other buffer size
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                    inputStream.close()
                }
            }
            Log.i("mausi", "copied all files")
            (object : ExecuteAsRootBase() {
                override fun getCommandsToExecute(): ArrayList<String> {
                    return arrayListOf("cp -r ${scriptsDirectory.absolutePath} /data/local/tmp && chmod +x /data/local/tmp/scripts/inject2.sh && sh /data/local/tmp/scripts/inject2.sh")
                }
            }).execute()
            //getActionBar()!!.setTitle("About")
            // getActionBar()!!.setDisplayShowTitleEnabled(true)
//        getActionBar()!!.setTitle("mhf")
            //val actionbar = getActionBar()
            //actionbar!!.setTitle("your title goes here.");
            //(activity as AppCompatActivity?)!!.supportActionBar!!.setTitle("gfd")
            //showdialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}