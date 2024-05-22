package com.ink.cltest1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    lateinit var txt : TextView
    lateinit var countTxt: TextView
    var cnt: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // onCreate: called when the activity is first created

        // Initialise views
        countTxt = findViewById(R.id.textCnt)
        txt = findViewById(R.id.text1)
        val btnInc : Button = findViewById(R.id.incCnt)
        val btnDec : Button = findViewById(R.id.decCnt)
        val btnDlg : Button = findViewById(R.id.dlg)

        // append onCreate to txt and show toast
        txt.append(" OnCreate called\n")
        Toast.makeText(this, "OnCreate Called Toast!", Toast.LENGTH_SHORT).show()

        btnInc.setOnClickListener {
            // increase count and update countTxt
            cnt++
            countTxt.setText(cnt.toString());
        }

        btnDec.setOnClickListener {
            // decrease count and update countTxt
            cnt--
            countTxt.setText(cnt.toString());
        }

        // dialogue builder for reset count
        val dialogBuilder = MaterialAlertDialogBuilder(this,
            com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
            .setTitle("Reset count?")
            .setMessage("Test message")
            .setIcon(resources.getDrawable(R.drawable.ic_android_black_24dp))
            .setNegativeButton("Cancel") { dialog, which ->
                // handle negative button action
            }
            .setPositiveButton("Confirm") { dialog, which ->
                cnt=0
                countTxt.setText(cnt.toString());
            }

        btnDlg.setOnClickListener {
            dialogBuilder.show()
        }

        // we can restore state either in onCreate()
        // or in onRestoreInstanceState()
        // note that the parameter in onCreate is nullable.
        // this is because when the activity is first created
        // there is nothing to restore (hence the bundle is null)
        // and therefore we need to do null check before restoring state
        /* if(savedInstanceState!=null){
            cnt = savedInstanceState.getInt("countKey", 69)
            // cnt will receive from bundle
            // the value with key "countKey"
            // if no such value exists with that key
            // it will be set to default value 69
            countTxt.setText(cnt.toString());
        } */

    }

    override fun onStart() {
        super.onStart()
        // onStart is called right after onCreate
        // at this state, the activity is visible
        // but the user can't yet interact with it

        // append onStart to txt and show toast
        txt.append(" -> onStart Called\n")
        Toast.makeText(this, "onStart Called Toast!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // onResume is called after onStart
        // at this state, the user can interact with the activity.
        // till the activity is in the foreground
        // it is at this state only

        // append onResume to txt and show toast
        txt.append(" -> onResume Called\n")
        Toast.makeText(this, "onResume Called Toast!", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        // onPause is called when the user is about
        // to put the activity into background,
        // or if in multiwindow mode, the focus
        // changes from current activity to another activity
        // onStop is called right after onPause

        // note: onPause() execution is very brief and
        // does not necessarily offer enough time to
        // perform save operations.
        // for this reason, don't use onPause()
        // to save application or user data

        // append onPause to txt and show toast
        txt.append(" -> onPause Called\n")
        Toast.makeText(this, "onPause Called Toast!", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        // when the activity is no longer visible to the user
        // it enters the stopped state (eg, a newly launched
        // activity covers the screen, or if the activity is
        // about to be terminated)

        // onStop is called right after onPause
        // at this state, the activity is no longer visible.
        // onSaveInstanceState is called to save the activity state.
        // later if the user returns to the activity, or if configuration
        // changes take place, onRestart() is called

        // append onStop to txt and show toast
        txt.append(" -> onStop Called\n")
        Toast.makeText(this, "onStop Called Toast!", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // onSaveInstanceState is called right after onStop.
        // this is used to handle configuration changes.
        // we can save the state (i.e. some variables) in a bundle
        // and this bundle will be used to restore state later on.

        // append onSaveInstanceState to txt and show toast
        txt.append(" -> onSaveInstanceState called\n")
        Toast.makeText(this, "onSaveInstanceState Called Toast!", Toast.LENGTH_SHORT).show()
        // store the integer cnt with key countKey in the bundle
        outState.putInt("countKey", countTxt.text.toString().toInt())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //onRestoreInstance state is called right after onStart
        // and before onResume, this is where we restore
        // the state of the activity which was saved earlier

        // append onRestoreInstanceState to txt and show toast
        txt.append(" -> onRestoreInstanceState called\n")
        Toast.makeText(this, "onRestoreInstanceState Called Toast!", Toast.LENGTH_SHORT).show()
        // restore the integer cnt from value in the savedInstanceState bundle
        cnt = savedInstanceState.getInt("countKey", 69)
        // and then set the value of cnt to corresponding TextView
        countTxt.setText(cnt.toString());
    }

    override fun onDestroy() {
        super.onDestroy()
        // onDestroy is called when the activity is destroyed
        // this might be caused due to either of the two reasons:
        // 1. the activity is finished (user completely dismissed
        //    the activity or finish() was called)
        // 2. device configuration changes
        // in the 2nd case, onCreate() will be called again
        // which means all the views will be initialised again
        // so any of the data the user might have entered
        // (think of registering a user, their name, email etc)
        // will be gone. And to prevent the same, onSaveInstanceState
        // and onRestoreInstanceState are used.
        txt.append(" -> onDestroy called\n")
        Toast.makeText(this, "onDestroy Called Toast!", Toast.LENGTH_SHORT).show()
    }

}

// Configuration changes: changes in device orientation (most frequent),
// theme (light/dark), language etc are configuration changes which
// cause the activity to be destroyed, and onCreate() must be called
// again, which causes loss of any data entered by user on that screen.
// to work around the same, we use onSaveInstanceState to save the state
// of activity in a bundle and onRestoreInstanceState to restore the state
// from the bundle provided (which we can do in onCreate as well).

// try the following in the app for a better understanding:
// -> go back to home screen from the app and then relaunch the app (don't kill it from memory)
//      you'll see what all states the activity goes through:
//      onPause, onStop and onSaveInstanceState when the activity goes goes from foreground to background
//      onStart, onResume when activity comes back to foreground from background
// -> change orientation (landscape to portrait/portrait to landscape)
//      onPause -> onStop -> onSaveInstanceState -> onDestroy -> onCreate -> onStart -> onRestoreInstanceState -> onResume
//      because of limitations set by Android, an app can't display more than 5 toasts in a row so you'll probably only see the first 5

// Homework:
// -> In this example, we have not saved the string from the TextView txt.
//    So, you have to save and then restore it on configuration changes.
// -> Read about and try implementing start startActivityForResult