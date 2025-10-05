package com.HeroxWar.HeroxCore.Dialog;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.dialog.Dialog;
import net.md_5.bungee.api.dialog.DialogBase;

public class HeroxDialog implements Dialog {

    private DialogBase dialogBase;
    private BaseComponent title;
    private BaseComponent content;

    /**
     * Costruttore base con titolo e contenuto predefiniti
     */
    public HeroxDialog() {
        this("§6§lDialogo Herox", "§7Contenuto di default del dialogo.");
    }

    /**
     * Costruttore personalizzato
     *
     * @param title    Titolo del dialogo
     * @param content  Contenuto/descrizione del dialogo
     */
    public HeroxDialog(String title, String content) {
        this.title = new TextComponent(title);
        this.content = new TextComponent(content);

        // DialogBase richiede almeno un titolo
        this.dialogBase = new DialogBase(this.title);
    }

    @Override
    public DialogBase getBase() {
        return dialogBase;
    }

    @Override
    public void setBase(DialogBase dialogBase) {
        this.dialogBase = dialogBase;
    }

    /**
     * Cambia il titolo del dialogo
     */
    public void setTitle(String title) {
        this.title = new TextComponent(title);
        this.dialogBase = new DialogBase(this.title);
    }

    /**
     * Cambia il contenuto del dialogo
     */
    public void setContent(String content) {
        this.content = new TextComponent(content);
    }

    public BaseComponent getTitle() {
        return title;
    }

    public BaseComponent getContent() {
        return content;
    }
}
