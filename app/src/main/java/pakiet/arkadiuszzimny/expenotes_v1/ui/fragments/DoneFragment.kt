package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
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
            if(it.isNotEmpty()) {
                archiveAdapter = ArchiveAdapter(it)
                recyclerView.adapter = archiveAdapter
            }
        })

        return mView
    }


}