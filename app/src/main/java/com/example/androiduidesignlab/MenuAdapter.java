package com.example.androiduidesignlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Menu> menuItems;
    private final OnMenuListener onMenuListener;

    public interface OnMenuListener {
        void onEditClick(long id, String name, double price, String image, String category);
        void onRemoveClick(long id);
    }

    public MenuAdapter(List<Menu> menuItems, OnMenuListener onMenuListener) {
        this.menuItems = menuItems;
        this.onMenuListener = onMenuListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menuItem = menuItems.get(position);
        holder.tvMenuName.setText(menuItem.getName());
        holder.tvMenuPrice.setText(String.format("£%.2f", menuItem.getPrice()));
        // In a real app, you'd load the image from the path. For now, we'll just set a placeholder.
        holder.ivMenuImage.setImageResource(R.drawable.ic_launcher_background);

        holder.btnEditMenu.setOnClickListener(v -> {
            if (onMenuListener != null) {
                onMenuListener.onEditClick(menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getImage(), menuItem.getCategory());
            }
        });

        holder.btnRemoveMenu.setOnClickListener(v -> {
            if (onMenuListener != null) {
                onMenuListener.onRemoveClick(menuItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMenuImage;
        TextView tvMenuName;
        TextView tvMenuPrice;
        Button btnEditMenu;
        Button btnRemoveMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenuImage = itemView.findViewById(R.id.ivMenuImage);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
            btnEditMenu = itemView.findViewById(R.id.btnEditMenu);
            btnRemoveMenu = itemView.findViewById(R.id.btnRemoveMenu);
        }
    }
}
