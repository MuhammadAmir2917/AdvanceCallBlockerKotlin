package com.example.advance.callblocker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advance.callblocker.R
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.dataloader.ProfileLoader
import com.example.advance.callblocker.models.Group
import kotlinx.android.synthetic.main.item_group.view.*

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.ViewHolder>(){

    private val groups  = mutableListOf<Group>()
    private var onItemClickListener : OnItemClickListener<Group>?= null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Group>){
        this.onItemClickListener = onItemClickListener
    }

    companion object{
        var isGroupDeleteShow = false
    }


    fun addItem(group : Group){
        if(isGroupDeleteShow)
            group.isShow=true
        this.groups.add(group)
        notifyItemInserted(groups.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group , parent , false)
        return ViewHolder(view)

    }

    override fun getItemCount() = groups.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(groups[position])
    }

    fun resetGroupItem(){
        groups.forEach {
            it.isShow=!isGroupDeleteShow
        }
        isGroupDeleteShow=!isGroupDeleteShow
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun onBind(group: Group) {
            val context = itemView.context
            itemView.tv_title.text = context.getString(R.string.group_name_count , group.name , group.count)

            itemView.iv_delete.visibility = when(group.isShow){
                true-> View.VISIBLE
                false-> View.GONE
            }

            itemView.group_item.setOnClickListener {
                onItemClickListener?.onItemClick(group)
            }

            itemView.group_item.setOnLongClickListener {
                resetGroupItem()
                true
            }
        }

    }

}