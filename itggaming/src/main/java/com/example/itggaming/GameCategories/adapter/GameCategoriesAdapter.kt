import com.example.itggaming.GameCategories.adapter.viewholder.CategoriesViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.viewholder.AdsViewHolder
import com.example.itggaming.GameLanding.api.adapter.viewholder.BlankViewHolder
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.example.itggaming.util.GamingLogCallbacks

class GameCategoriesAdapter(adUnitId:String, dataset: ArrayList<Games>, rv:RecyclerView): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataset=dataset
    val adUnitId=adUnitId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater=LayoutInflater.from(parent.context)
        when(viewType){
            0->{
                val view: View =inflater.inflate(R.layout.layout_category_page_item,parent,false)
                return CategoriesViewHolder(view)
            }
            1->{
                val view: View =inflater.inflate(R.layout.layout_landing_ads,parent,false)
                return AdsViewHolder(view)
            }
            else->{
                val view: View =inflater.inflate(R.layout.layout_blank,parent,false)
                return BlankViewHolder(view)
            }
        }
    }




    override fun getItemCount(): Int {
        return dataset.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            0->{
                (holder as CategoriesViewHolder).bind(dataset.get(position),position)
            }
            1->{
                (holder as AdsViewHolder).bind(adUnitId)
            }
            else->{
                (holder as BlankViewHolder).bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataset.get(position).type){
            "webview"->{
                0
            }
            "ads"->{
                1
            }
            else->{
                2
            }
        }
        return super.getItemViewType(position)
    }
}