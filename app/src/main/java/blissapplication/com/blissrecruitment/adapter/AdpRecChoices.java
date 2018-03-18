package blissapplication.com.blissrecruitment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.interfaces.IRecyclerOnClickListener;
import blissapplication.com.blissrecruitment.model.Choice;
import blissapplication.com.blissrecruitment.model.Question;


public class AdpRecChoices extends RecyclerView.Adapter<AdpChoicesVH> {

    private List<Choice> choices;
    private Context ctx;
    private IRecyclerOnClickListener iRecyclerOnClickListener;
    private boolean answer;

    public AdpRecChoices(Context context, List<Choice> choices, IRecyclerOnClickListener listener, boolean answer) {
        this.choices = choices;
        this.ctx = context;
        this.iRecyclerOnClickListener = listener;
        this.answer = answer;
    }

    @Override
    public AdpChoicesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.ly_choice_list, parent
                , false);

        AdpChoicesVH holder = new AdpChoicesVH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder (final AdpChoicesVH viewHolder, int position) {
        Choice choice = choices.get(position);

        viewHolder.txtChoice.setText(choice.getChoice());
        viewHolder.txtTotal.setText("("+String.valueOf(choice.getVotes())+")");

        viewHolder.bind(choice, iRecyclerOnClickListener);

        if (answer) {
            if (choice.getVotes() == 1) {
                viewHolder.txtTotal.setText("("+String.valueOf(choice.addVote())+")");
                viewHolder.imgCheck.setImageResource(R.mipmap.ic_checked);
            } else {
                viewHolder.imgCheck.setImageResource(R.mipmap.ic_check_disable);
            }

            viewHolder.imgCheck.setEnabled(false);

        }

    }

    @Override
    public int getItemCount() {return choices.size();}
}


class AdpChoicesVH extends RecyclerView.ViewHolder {

    TextView txtChoice;
    TextView txtTotal;
    ImageButton imgCheck;

    public AdpChoicesVH(final View itemView){
        super(itemView);

        txtChoice = (TextView) itemView.findViewById(R.id.tvChoice);
        txtTotal = (TextView) itemView.findViewById(R.id.tbTotal);
        imgCheck =  (ImageButton) itemView.findViewById(R.id.ibCheck);
    }

    public void bind(final Choice item, final IRecyclerOnClickListener listener) {
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListenerChoice(item);
            }
        });
    }
}