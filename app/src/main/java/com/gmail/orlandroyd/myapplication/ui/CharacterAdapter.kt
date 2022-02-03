package com.gmail.orlandroyd.myapplication.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.orlandroyd.myapplication.databinding.ItemCharacterBinding
import com.gmail.orlandroyd.myapplication.model.Character

class CharacterAdapter :
    ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    /**
     * ViewHolder
     */
    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // bind function
        @SuppressLint("SetTextI18n")
        fun bind(item: Character) {
            binding.apply {

                // set real name
                tv1.text = item.actor
                // set character name
                tv2.text = item.name
                // set avatar image
                imgAvatar.load(item.image)

            }
        }
    }

    /**
     * DiffCallback
     */
    class DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }

}