package com.example.myapplication.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.R

abstract class BaseFragment <T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(){

    var mActivity: BaseActivity<*, *>? = null
    private var mViewDataBinding: T? = null
    private val mViewModel: V? = null
    private var progressDialog: Dialog? = null
    private var progressText: TextView? = null



    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity<*, *>
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    open fun getBaseActivity(): BaseActivity<*, *>? {
        return mActivity
    }


    open fun showProgress(message: String?) {
        Log.d("BaseFragment","loader33")
        val msg = Message.obtain()
        msg.what = BaseActivity.HANDLER_SHOW_PROGRESS
        msg.obj = message
        mProgressHandler.sendMessage(msg)
    }

    open fun dismissProgress() {
        mProgressHandler.sendEmptyMessage(BaseActivity.HANDLER_DISMISS_PROGRESS)
    }

    private val mProgressHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                BaseActivity.HANDLER_SHOW_PROGRESS -> {
                    Log.d("BaseFragment","loader44")
                    showLoader(msg.obj as String?)
                }
                BaseActivity.HANDLER_DISMISS_PROGRESS -> {
                    dismissLoader()
                }
            }
        }
    }

    private fun showLoader(message: String?) {
        if (activity != null && !activity?.isFinishing!!) {
            if (progressDialog == null) {
                initLoadingDialog()
            }
            if (message != null && !TextUtils.isEmpty(message)) {
                progressText?.text = message
            } else {
                progressText?.text = ""
            }
            progressDialog?.show()
        }
    }

    private fun dismissLoader(){
        if (progressDialog != null && progressDialog?.isShowing!!) {
            //get the Context object that was used to great the dialog
            val context = (progressDialog!!.context as ContextWrapper).baseContext
            // if the Context used here was an activity AND it hasn't been finished or destroyed
            // then dismiss it
            if (context is Activity) {
                if (!context.isFinishing && !context.isDestroyed) {
                    dismissWithTryCatch(progressDialog)
                }
            } else  // if the Context used wasn't an Activity, then dismiss it too
                dismissWithTryCatch(progressDialog)
        }
    }

    private fun initLoadingDialog() {
        activity?.let { activity ->
            if (!activity.isFinishing) {
                Log.d("BaseFragment","loader55")
                progressDialog = Dialog(activity)
                progressDialog?.setCancelable(false)
                progressDialog?.setCanceledOnTouchOutside(false)
                if (progressDialog?.window != null) {
                    progressDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
                    progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                    progressDialog?.window?.statusBarColor = activity.getColor(R.color.dn_loader_transparent)
                }
                val inflater = layoutInflater
                val dialogView: View = inflater.inflate(R.layout.progressbar_view, null)
                progressDialog?.setContentView(dialogView)
            }
        }
    }

    open fun dismissWithTryCatch(dialog: Dialog?) {
        try {
            progressDialog?.dismiss()
            progressDialog = null
        } catch (e: IllegalArgumentException) {
            // Do nothing.
        } catch (e: Exception) {
            // Do nothing.
        } finally {
            progressDialog = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BaseFragment", "baseFragment onDestroyView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.executePendingBindings()
    }
}