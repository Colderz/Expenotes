package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.archive_card_layout.view.*
import kotlinx.android.synthetic.main.fragment_done.*
import kotlinx.android.synthetic.main.fragment_done.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import pakiet.arkadiuszzimny.expenotes_v1.ui.ArchiveViewModel
import pakiet.arkadiuszzimny.expenotes_v1.ui.adapters.ArchiveAdapter

@AndroidEntryPoint
class DoneFragment : Fragment() {

    private val viewModel: ArchiveViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var archiveAdapter: ArchiveAdapter
    private lateinit var listOfGoals: LiveData<List<GoalItem>>
    var listOfArchiveGoals: List<GoalItem> = listOf()
    var currencyMainStr: String = "PLN"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mView: View = inflater.inflate(R.layout.fragment_done, container, false)
        recyclerView = mView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        listOfGoals = viewModel.getAllGoals()

        listOfGoals.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for(item in it) {
                    if(item.type.substring(0,2).equals("cu")) {
                        currencyMainStr = item.type.substring(8,11)
                }
                }
                listOfArchiveGoals = viewModel.filterArchiveGoals(it)
                archiveAdapter = ArchiveAdapter(listOfArchiveGoals, currencyMainStr)
                recyclerView.adapter = archiveAdapter
                var doneAmount = 0
                var archiveAmount = 0
                var doneRatio = 0
                var archiveRatio = 0
                for (item in listOfArchiveGoals) {
                    archiveRatio++
                    if (item.state >= item.goal) {
                        doneRatio++
                    }
                    doneAmount += item.state
                    archiveAmount += item.goal
                }
                setProgressBars(doneRatio, doneAmount, archiveRatio, archiveAmount, mView)
            }
        })

        val itemTouchHelperCallback = ItemTouchHelper(simpleCallback)
        itemTouchHelperCallback.attachToRecyclerView(recyclerView)

        return mView
    }

    fun setProgressBars(leftDone: Int, rightDone: Int, leftArchive: Int, rightArchive: Int, view: View) {
        view.done_amount.text = rightDone.toString()
        view.archive_amount.text = rightArchive.toString()
        view.done_ratio.text = leftDone.toString()
        view.archive_ratio.text = leftArchive.toString()
        view.progressBarCounter.max = leftArchive
        view.progressBarCounter.progress = leftDone
        view.progressBarArchivedGoals.max = rightArchive
        view.progressBarArchivedGoals.progress = rightDone
    }

    private var simpleCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {

        lateinit var deletedGoal: GoalItem

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        fun undoDeleted() {
            Snackbar.make(
                recyclerView, "Archived goal deleted",
                Snackbar.LENGTH_LONG
            ).setAction("Undo", {
                val itemGoal: GoalItem = viewModel.getDataAndCreateGoal(deletedGoal.type, deletedGoal.goal, deletedGoal.state)
                viewModel.upsert(itemGoal)
            }).show()
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            when(direction) {
                ItemTouchHelper.LEFT -> {
                    deletedGoal = listOfArchiveGoals.get(position)
                    viewModel.deleteGoal(deletedGoal)
                    undoDeleted()
                }
                ItemTouchHelper.RIGHT -> {
                    deletedGoal = listOfArchiveGoals.get(position)
                    viewModel.deleteGoal(deletedGoal)
                    undoDeleted()
                }
            }
        }
    }

}