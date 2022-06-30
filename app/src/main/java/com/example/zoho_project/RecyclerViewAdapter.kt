package com.example.zoho_project

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem
import kotlin.collections.ArrayList

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.repoListAdapter>(), Filterable {

    private val allRepoList = ArrayList<TrendingRepoItem>()
    private val repoListAll = ArrayList<TrendingRepoItem>()

    inner class repoListAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.repo_name)
        val description = itemView.findViewById<TextView>(R.id.desc_text)
        val languagetxt = itemView.findViewById<TextView>(R.id.lang_text)
        val startext = itemView.findViewById<TextView>(R.id.star_text)
        val languageCircle = itemView.findViewById<CardView>(R.id.lang_circle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): repoListAdapter {
        val viewHolder = repoListAdapter(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_format, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: repoListAdapter, position: Int) {
        val positionOnScreen = allRepoList[position]
        holder.name.text = positionOnScreen.name
        holder.description.text = positionOnScreen.description
        holder.languagetxt.text = positionOnScreen.language
        holder.startext.text = positionOnScreen.stars.toString()
        holder.languageCircle.setCardBackgroundColor(Color.parseColor(positionOnScreen.languageColor))
    }

    override fun getItemCount(): Int {
        return allRepoList.size
    }

    fun updateAll(item:ArrayList<TrendingRepoItem>){
        allRepoList.clear()
        allRepoList.addAll(item)
        repoListAll.addAll(item)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filteredList = ArrayList<TrendingRepoItem>()
                if(p0.toString().isEmpty()){
                    filteredList.addAll(repoListAll)
                }
                else{
                    for(item in repoListAll){
                        if(item.name.lowercase().contains(p0.toString().lowercase()))
                            filteredList.add(item)
                        else if(item.description.lowercase().contains(p0.toString().lowercase()))
                            filteredList.add(item)
                    }
                }
                val filteredResult = FilterResults()
                filteredResult.values = filteredList
                return filteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                allRepoList.clear()
                allRepoList.addAll(p1?.values as Collection<TrendingRepoItem>)
                notifyDataSetChanged()
            }
        }
    }

}


