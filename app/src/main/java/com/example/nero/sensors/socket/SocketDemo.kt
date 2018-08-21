package com.example.nero.sensors.socket

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.nero.sensors.R
import kotlinx.android.synthetic.main.activity_socket_demo.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SocketDemo : AppCompatActivity() {

    lateinit var server:Server
    lateinit var msg:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket_demo)

        msg=findViewById(R.id.msg)

        //sendMessage("hahahahahahha")
        server=Server(this@SocketDemo)
        val x = server.ipAddress +":"+server.port
        txt.text = x
    }

    private fun sendMessage(msg: String) {

        val handler = Handler()
        val thread = Thread(Runnable {
            try {
                //Replace below IP with the IP of that device in which server socket open.
                //If you change port then change the port number in the server side code also.

                //val s = Socket("192.168.43.101", 9002)
                val s = Socket("wss://echo.websocket.org",443)

                val out = s.getOutputStream()

                val output = PrintWriter(out)

                output.println(msg)
                output.flush()
                val input = BufferedReader(InputStreamReader(s.getInputStream()))
                val st = input.readLine()

                handler.post {
                    val s = txt.text.toString()
                    if (st.trim { it <= ' ' }.isNotEmpty())
                        txt.text = "$s\nFrom Server : $st"
                }

                output.close()
                out.close()
                s.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })

        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        server.onDestroy()
    }

}