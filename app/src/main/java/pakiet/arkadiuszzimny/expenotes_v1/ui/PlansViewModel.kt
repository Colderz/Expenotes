package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_plans.*
import kotlinx.coroutines.*
import pakiet.arkadiuszzimny.expenotes_v1.data.db.GoalRepository
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

class PlansViewModel @ViewModelInject constructor(
    application: Application,
    private var goalRepository: GoalRepository
) : ViewModel() {


    private var allGoal: Deferred<LiveData<List<GoalItem>>> =
        goalRepository.getAllGoalsAsync()
    val ENTERGOAL_FRAGMENT = 1
    val CHANGEDEPO_FRAGMENT = 1
    val MANAGEGOAL_FRAGMENT = 3

    private var imageUrl = "https://images.unsplash.com/photo-1606926730770-218d179a690e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"

    fun loadImageUsingGlide(
        fragment: Fragment,
        progressBar: ProgressBar,
        imageView: ImageView,
    ) {
        progressBar.visibility = View.VISIBLE
        Glide.with(fragment).load(imageUrl).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.VISIBLE
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.INVISIBLE
                return false
            }
        }).into(imageView)
    }

    fun saveData(context: Context, depPlus: TextView) {
        val insertedText = depPlus.text.toString()
        val sharedPreferences = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY", insertedText)
        }.apply()
    }

    fun loadData(context: Context, depPlus: TextView) {
        val sharedPreferences = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val savedString: String? = sharedPreferences.getString("STRING_KEY", null)

        if(savedString!=null) {
            depPlus.text = savedString
        }
    }

    fun upsert(item: GoalItem) {
        goalRepository.upsert(item)
    }

    fun deleteGoal(item: GoalItem) {
        goalRepository.delete(item)
    }

    fun getAllGoals(): LiveData<List<GoalItem>> = runBlocking {
        allGoal.await()
    }


}