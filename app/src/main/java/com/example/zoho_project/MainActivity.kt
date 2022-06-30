package com.example.zoho_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem
import com.example.zoho_project.Utils.MyUtils
import com.example.zoho_project.ViewModel.MainViewModel
import com.example.zoho_project.ViewModel.ViewModelFactory
import com.example.zoho_project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var repoList = ArrayList<TrendingRepoItem>()
    private lateinit var binding:ActivityMainBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repository = (application as MyApplication).repository

        //now we have all thing set, so we can create viewModel
        mainViewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]
        recyclerView = findViewById(R.id.allRepoRV)
        adapter = RecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.progressBar.visibility = View.VISIBLE


        //In viewModel we have already defined a livedata "allRepos" which get data from repository
        //so "allRepos" is livedata so we can observe it.
        if(MyUtils.isInternetAvailable(applicationContext) == true) {
            mainViewModel.allRepos.observe(this, Observer {
                repoList.clear()
                it.forEach {itr ->
                    repoList.add(itr)
                }
                binding.progressBar.visibility = View.GONE
                adapter.updateAll(repoList)
                recyclerView.adapter = adapter
            })
        }
        else {
              binding.progressBar.visibility = View.GONE
              binding.networkErrorView.visibility = View.VISIBLE
        }


        //try again button
        binding.tryAgainBtn.setOnClickListener {
            if(MyUtils.isInternetAvailable(applicationContext) == true)
                offlineToOnline()
            else
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }

        //go offline button
        binding.goOfflineBtn.setOnClickListener{
            offlineData()
        }

        //pull refresh
        binding.pullRefresh.setOnRefreshListener {
            if(MyUtils.isInternetAvailable(applicationContext) == true) {
                offlineToOnline()
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                binding.pullRefresh.isRefreshing = false
            }
            else {
                binding.progressBar.visibility = View.GONE
                binding.networkErrorView.visibility = View.VISIBLE
                binding.pullRefresh.isRefreshing = false
            }
        }
    }

    //called when refresh and try again is clicked
    private fun offlineToOnline() {
        binding.networkErrorView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        mainViewModel.refreshList()
        mainViewModel.allRepos.observe(this, Observer {
            repoList.clear()
            it.forEach {itr ->
                repoList.add(itr)
            }
            binding.progressBar.visibility = View.GONE
            adapter.updateAll(repoList)
            recyclerView.adapter = adapter
        })
    }

    //called when go offline button clicked
    private fun offlineData(){
        binding.networkErrorView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        mainViewModel.getAllOfflineData()
        mainViewModel.allOfflineRepos.observe(this, Observer {
            repoList.clear()
            it.forEach {itr ->
                repoList.add(itr)
            }
            binding.progressBar.visibility = View.GONE
            adapter.updateAll(repoList)
            recyclerView.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu?.findItem(R.id.search_repo_action)
        val searchView : SearchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}