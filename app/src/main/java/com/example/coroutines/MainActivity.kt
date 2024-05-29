package com.example.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutines.ui.theme.CoroutinesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    val Tag = "KOTLINFU"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        GlobalScope.launch {
            delay(3000L)
            Log.d(Tag,"Coroutine says hello from thread ${Thread.currentThread().name}")
            val networkCallAnswer1 = doNetworkCall1()
            val networkCallAnswer2 = doNetworkCall2()
            Log.d(Tag,networkCallAnswer1)
            Log.d(Tag,networkCallAnswer2)
        }

//        context Switching
        GlobalScope.launch(Dispatchers.IO) {
            val answer = doNetworkCall1()
            withContext(Dispatchers.Main){
                Log.d(Tag,"Setting text in thread ${Thread.currentThread().name}")
            }
        }


        Log.d(Tag,"hello from thread ${Thread.currentThread().name}") //main thread

        //runblocking
       Log.d(Tag,"before Run blocking")
        runBlocking {
            //IN RUNBLOCK we can use suspended function in main block
            Log.d(Tag,"Run blocking started")
            delay(10000L)
            Log.d(Tag,"Run blocking Ended")
        }
        Log.d(Tag,"After Run Blocking")


        //job
        val JobTag = "JOB"
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5){
                Log.d(JobTag,"Still running")
                delay(1000L)
            }
        }
        runBlocking {
            job.join()
            Log.d(JobTag,"Main Thread is Continuing....")
        }



    }
//suspend function
    suspend fun doNetworkCall1(): String{
        delay(3000L)
        return "this is network call1"
    }

    suspend fun doNetworkCall2(): String{
        delay(3000L)
        return "this is network call2"
    }
}