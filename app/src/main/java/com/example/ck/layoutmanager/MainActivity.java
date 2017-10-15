package com.example.ck.layoutmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerview);

        MyLayoutManage myLayoutManage=new MyLayoutManage(this);
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        myLayoutManage.addFlowView(new MyLayoutManage.ViewBean(imageView,300,300,1));

        recyclerView.setLayoutManager(myLayoutManage);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType==100) {
                    return new RecyclerView.ViewHolder(View.inflate(parent.getContext(),R.layout.floata,null)) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }
                        else {
                    TextView tv = new TextView(parent.getContext());
                    tv.setText("fffffffffffff");
                    return new RecyclerView.ViewHolder(tv) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                if(position==10||position==99){
                    holder.itemView.setBackgroundColor(0xffff0000);
                    if(position==99)
                        holder.itemView.setBackgroundResource(R.mipmap.ic_launcher);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(),"fddd"+position,0).show();
                        }
                    });
                }

            }

            @Override
            public int getItemViewType(int position) {
                Log.i("--", "getItemViewType: "+position);
                if(position==99)
                    return 100;
                return super.getItemViewType(position);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }
}
