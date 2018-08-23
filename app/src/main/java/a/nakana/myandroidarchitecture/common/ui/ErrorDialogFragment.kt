package a.nakana.myandroidarchitecture.common.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class ErrorDialogFragment(val message : String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog
                .Builder(activity!!)
                .setTitle("エラー")
                .setMessage("エラーがおきた模様です($message)")
                .setPositiveButton("OK"){
                    dialog, id -> dialog.dismiss()
                }
                .create()
    }
//
//    override fun onPause() {
//        super.onPause()
//        dismiss()
//    }
}
