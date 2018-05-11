package model;

public interface Callback {

    void onClick(int btnID);
    void onOpponentClick(int btnID);

    void onGameOver();
    void onUnlockButtons();
    void onReset();

}
