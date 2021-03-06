package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_plans.*
import kotlinx.android.synthetic.main.fragment_plans.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import pakiet.arkadiuszzimny.expenotes_v1.ui.PlansViewModel
import java.util.*

@AndroidEntryPoint
class PlansFragment : Fragment() {

    private val viewModel: PlansViewModel by viewModels()
    private lateinit var listOfGoals: LiveData<List<GoalItem>>
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true
    var dialogInstance: NewGoalDialogFragment? = null
    var dialogInstanceMg: InfoGoalDialogFragment? = null
    var dialogInstanceCurr: CurrencyDialogFragment? = null
    var mainCurrencyFragment: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val goalsFragmentView: View = inflater.inflate(R.layout.fragment_plans, container, false)
        viewModel.loadImageUsingGlide(
            this,
            goalsFragmentView.fragmentOneProgressBar,
            goalsFragmentView.createGoalImage
        )

        goalsFragmentView.createGoalImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (dialogInstance != null) {
                    dialogInstance!!.dismiss()
                }
                dialogInstance = NewGoalDialogFragment.newInstance()
                dialogInstance!!.setTargetFragment(this@PlansFragment, viewModel.ENTERGOAL_FRAGMENT)
                dialogInstance!!.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance!!.show(
                    parentFragmentManager.beginTransaction(),
                    NewGoalDialogFragment.TAG
                )
            }
        })

        goalsFragmentView.btn_curr.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (dialogInstanceCurr != null) {
                    dialogInstanceCurr!!.dismiss()
                }
                dialogInstanceCurr = CurrencyDialogFragment.newInstance(viewModel.arrayOfCurrency)
                dialogInstanceCurr!!.setTargetFragment(
                    this@PlansFragment,
                    viewModel.NEWCURR_FRAGMENT
                )
                dialogInstanceCurr!!.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstanceCurr!!.show(
                    parentFragmentManager.beginTransaction(),
                    CurrencyDialogFragment.TAG
                )
            }
        })

        goalsFragmentView.mainGoalCard.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (dialogInstanceMg != null) {
                    dialogInstanceMg!!.dismiss()
                }
                val dialogInstanceMg = InfoGoalDialogFragment.newInstance()
                dialogInstanceMg!!.setTargetFragment(
                    this@PlansFragment,
                    viewModel.MANAGEGOAL_FRAGMENT
                )
                dialogInstanceMg!!.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstanceMg!!.show(
                    parentFragmentManager.beginTransaction(),
                    InfoGoalDialogFragment.TAG
                )
            }
        })

        goalsFragmentView.editdep_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogInstance = EditDepDialogFragment.newInstance()
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.CHANGEDEPO_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(
                    parentFragmentManager.beginTransaction(),
                    EditDepDialogFragment.TAG
                )
            }
        })

        val scale = this.resources.displayMetrics.density
        goalsFragmentView.mainGoalCard.cameraDistance = 8000 * scale
        goalsFragmentView.mainGoalCardBack.cameraDistance = 8000 * scale

        front_anim =
            AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet

        goalsFragmentView.flip_btn.setOnClickListener {
            flip_btn.isEnabled = false
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
            flip_btn.startAnimation(animation)
            if (isFront) {
                front_anim.setTarget(goalsFragmentView.mainGoalCard)
                back_anim.setTarget(goalsFragmentView.mainGoalCardBack)
                front_anim.start()
                back_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(goalsFragmentView.mainGoalCardBack)
                back_anim.setTarget(goalsFragmentView.mainGoalCard)
                back_anim.start()
                front_anim.start()
                isFront = true
            }

            Handler(Looper.getMainLooper()).postDelayed({
                flip_btn.isEnabled = true
            }, 1001)

        }


        return goalsFragmentView
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(requireContext(), depPlus)
        listOfGoals = viewModel.getAllGoals()
        listOfGoals.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (item in it) {
                    if (item.type.equals("main")) {
                        amountGoal.text = item.goal.toString()
                        stateGoal.text = item.state.toString()
                        progressBarGoal.max = Integer.valueOf(item.goal.toString())
                        progressBarGoal.progress = Integer.valueOf(item.state.toString())
                    }
                    if (item.type.equals("fut1")) {
                        amountGoal1.text = item.goal.toString()
                    }
                    if (item.type.equals("fut2")) {
                        amountGoal2.text = item.goal.toString()
                    }
                    if(item.type.substring(0,2).equals("cu")) {
                        setGoalsCurrency(item.type.substring(8,11))
                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            viewModel.ENTERGOAL_FRAGMENT -> if (resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValue: String = bundle!!.getString("value", "error")
                var resultValue1 = bundle!!.getString("value1", "error")
                var resultValue2 = bundle!!.getString("value2", "error")
                var resultValueDep = bundle!!.getString("valueDep", "error")
                if (!resultValue.equals("error")) {
                    amountGoal.text = resultValue
                    val item = viewModel.createGoal("main", Integer.valueOf(resultValue), 0)
                    viewModel.upsert(item)
                    progressBarGoal.max = Integer.valueOf(resultValue)
                    progressBarGoal.progress = 0
                }
                if (!resultValue1.equals("error")) {
                    amountGoal1.text = resultValue1
                    val item1 = viewModel.createGoal("fut1", Integer.valueOf(resultValue1), 0)
                    viewModel.upsert(item1)
                }
                if (!resultValue2.equals("error")) {
                    amountGoal2.text = resultValue2
                    val item2 = viewModel.createGoal("fut2", Integer.valueOf(resultValue2), 0)
                    viewModel.upsert(item2)
                }
                if (!resultValueDep.equals("error")) {
                    depPlus.text = resultValueDep
                    viewModel.saveData(requireContext(), depPlus)
                }
                val needed: Int =
                    Integer.valueOf(amountGoal.text.toString()) + Integer.valueOf(amountGoal1.text.toString()) + Integer.valueOf(
                        amountGoal2.text.toString()
                    )
                viewModel.upsert(viewModel.createGoal("wallet", needed, 0))
            }
            viewModel.MANAGEGOAL_FRAGMENT -> if (resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValue: String = bundle!!.getString("delete", "error")
                var resultValueArchive: String = bundle!!.getString("archive", "error")
                var needed = 0
                if (!(resultValue.equals("error"))) {
                    if (!amountGoal1.equals("0")) {
                        if (!amountGoal.text.toString().equals("0")) {
                            val itemMain = viewModel.createGoal(
                                "main",
                                Integer.valueOf(amountGoal1.text.toString()),
                                0
                            )
                            needed += itemMain.goal
                            viewModel.upsert(itemMain)
                            if (!amountGoal2.equals("0")) {
                                val item1 = viewModel.createGoal(
                                    "fut1",
                                    Integer.valueOf(amountGoal2.text.toString()),
                                    0
                                )
                                needed += item1.goal
                                viewModel.upsert(
                                    item1
                                )
                                viewModel.upsert(viewModel.createGoal("fut2", 0, 0))
                            } else {
                                viewModel.upsert(viewModel.createGoal("fut1", 0, 0))
                            }
                        }
                    }
                    viewModel.upsert(viewModel.createGoal("wallet", needed, 0))
                }
                if (!resultValueArchive.equals("error")) {
                    if (!amountGoal.text.toString().equals("0")) {
                        val itemArchive = viewModel.createGoal(
                            "archive" + "${Date().time}",
                            Integer.valueOf(amountGoal.text.toString()),
                            Integer.valueOf(stateGoal.text.toString())
                        )
                        viewModel.upsert(itemArchive)
                        if (!amountGoal1.equals("0")) {
                            val itemMain = viewModel.createGoal(
                                "main",
                                Integer.valueOf(amountGoal1.text.toString()),
                                0
                            )
                            needed += itemMain.goal
                            viewModel.upsert(itemMain)
                            if (!amountGoal2.equals("0")) {
                                val item1 = viewModel.createGoal(
                                    "fut1",
                                    Integer.valueOf(amountGoal2.text.toString()),
                                    0
                                )
                                needed += item1.goal
                                viewModel.upsert(item1)
                                viewModel.upsert(viewModel.createGoal("fut2", 0, 0))
                            } else {
                                viewModel.upsert(viewModel.createGoal("fut1", 0, 0))
                            }
                        }
                        viewModel.upsert(viewModel.createGoal("wallet", needed, 0))
                    }
                }
            }
            viewModel.NEWCURR_FRAGMENT -> if (resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValueCurr: String = bundle!!.getString("currency", "error")
                if (!(resultValueCurr.equals("error"))) {
                    viewModel.upsert(viewModel.createGoal("currency${resultValueCurr}", 0, 0))
                    setGoalsCurrency(resultValueCurr)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletOkFrom.setOnClickListener {
            if (!amountGoal.toString().equals("0")) {
                var stateBefore = Integer.valueOf(stateGoal.text.toString())
                var stateAfter =
                    Integer.valueOf(stateGoal.text.toString()) + Integer.valueOf(depPlus.text.toString())
                var amountGoalVar = Integer.valueOf(amountGoal.text.toString())
                if (stateAfter >= amountGoalVar) {
                    if (!amountGoal.text.toString().equals("0")) {
                        progressBarGoal.progress = stateBefore + (amountGoalVar - stateBefore)
                        Toast.makeText(context, "Target Achieved", Toast.LENGTH_LONG).show()
                        stateGoal.text = amountGoal.text.toString()
                        val itemChanged1 =
                            viewModel.createGoal("main", amountGoalVar, amountGoalVar)
                        viewModel.upsert(itemChanged1)
                        val dialogInstance = InfoGoalDialogFragment.newInstance()
                        dialogInstance.setTargetFragment(
                            this@PlansFragment,
                            viewModel.MANAGEGOAL_FRAGMENT
                        )
                        dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                        dialogInstance.show(
                            parentFragmentManager.beginTransaction(),
                            InfoGoalDialogFragment.TAG
                        )
                    }
                } else {
                    val itemChanged = viewModel.createGoal("main", amountGoalVar, stateAfter)
                    viewModel.upsert(itemChanged)
                    progressBarGoal.progress = stateAfter
                }
            }
        }
    }

    fun setGoalsCurrency(currencyStr: String) {
        currencyBack.text = currencyStr
        currency.text = currencyStr
        currency1.text = currencyStr
        currency2.text = currencyStr
    }


}