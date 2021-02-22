package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.app.Application
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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
import kotlinx.coroutines.*
import pakiet.arkadiuszzimny.expenotes_v1.data.db.GoalRepository
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

class PlansViewModel @ViewModelInject constructor(application: Application, private var goalRepository: GoalRepository) : ViewModel() {


    private var allGoal: Deferred<LiveData<List<GoalItem>>> =
        goalRepository.getAllGoalsAsync()
    val ENTERGOAL_FRAGMENT = 1
    val CHANGEDEPO_FRAGMENT = 1
    val MANAGEGOAL_FRAGMENT = 3
    private val imageUrl = "https://images.unsplash.com/photo-1507925921958-8a62f3d1a50d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1355&q=80"

    fun loadImageUsingGlide(
        fragment: Fragment,
        progressBar: ProgressBar,
        imageView: ImageView,
    )
    {
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

    fun upsert(item: GoalItem) {
        goalRepository.upsert(item)
    }

    fun deleteGoal(item: GoalItem) {
        goalRepository.delete(item)
    }

    fun getAllGoals() : LiveData<List<GoalItem>> = runBlocking {
        allGoal.await()
    }


}