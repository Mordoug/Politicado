package com.example.morganseielstad.politicado

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.R.attr.layoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import java.sql.Time

class PoliticianListFragment : Fragment() {
    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    val pController: PoliticianController = PoliticianController()
    var myDataset = ArrayList<Politician>()

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.politician_list)
        initData()

        Log.i("recycle", "onCreate")
        //myDataset = intent.getSerializableExtra("politician") as ArrayList<Politician>


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.i("Recycle", "onCreateView")

        val rootView = inflater.inflate( R.layout.politician_list, container, false).apply { tag = "TAG" }

        recyclerView = rootView.findViewById(R.id.recycle_poly)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
        layoutManager = LinearLayoutManager(activity)

        layoutManager = this@PoliticianListFragment.layoutManager

        recyclerView.adapter = MyAdapter(myDataset) { politician ->
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            Log.d("ft:", id.toString())
            ft.replace(R.id.main_fragment, PoliticianFragment(politician)) //pass ID Somehow in future
            ft.addToBackStack(ft.toString())
            ft.commit()
        }


        return rootView

    }

    private fun initData() {
        doAsyncResult {
            pController.callPoliticians()
        }.get()

        myDataset = pController.getPoli()
    }

}