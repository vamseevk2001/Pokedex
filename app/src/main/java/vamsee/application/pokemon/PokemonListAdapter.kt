package vamsee.application.pokemon

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PokemonListAdapter(private val listener: PokeClick): RecyclerView.Adapter<PokeViewHolder>() {

    private val items: ArrayList<pokemon_data_class> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemons, parent, false)
        val viewHolder = PokeViewHolder(view)
        view.setOnClickListener{
               listener.onPokeClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val color: Int = holder.card.context.resources.getColor(R.color.grassLight)
        val currentItem = items[position]
        holder.loader.visibility = View.VISIBLE
        Glide.with(holder.itemView.context).load(currentItem.img_url).listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
            ): Boolean {
                holder.loader.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
            ): Boolean {
                holder.loader.visibility = View.GONE
                return false
            }
        }

        ).into(holder.img)
        holder.name.text = currentItem.name
        holder.pokeType.text = currentItem.type[0] as CharSequence?
        when(currentItem.type[0] as CharSequence?){
            "Grass" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.grassDark))
                holder.card.setCardBackgroundColor(color)
            }
            "Fire" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.fireDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.fireLight))
            }
            "Water" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.waterDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.watersLight))
            }
            "Electric" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.electricDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.electricLight))
            }
            "Bug", "Poison" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.bugDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.bugLight))
            }
            "Ground" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.groundDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.groundLight))
            }
            "Normal", "Psychic" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.normalDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.normalLight))
            }
            "Dragon" -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.dragonDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.dragonLight))
            }
            else -> {
                holder.type.setCardBackgroundColor(holder.type.context.resources.getColor(R.color.defaultDark))
                holder.card.setCardBackgroundColor(holder.card.context.resources.getColor(R.color.defaultLight))
            }
        }


    }



    override fun getItemCount(): Int {
        return items.size
    }

    fun updatePokemons(updated: ArrayList<pokemon_data_class>){
        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
    }

}


class PokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val img: ImageView = itemView.findViewById(R.id.pokemon)
    val name: TextView = itemView.findViewById(R.id.poke_name)
    val type: CardView = itemView.findViewById(R.id.type)
    val pokeType: TextView = itemView.findViewById(R.id.pokeType)
    val card: CardView = itemView.findViewById(R.id.card)
    val loader: LottieAnimationView = itemView.findViewById(R.id.loading)
}

interface PokeClick {
    fun onPokeClick(item: pokemon_data_class)
}