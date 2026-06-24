package com.HeroxWar.HeroxCore.Utils;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.dialog.DialogBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DialogTest {

    private Dialog dialog;

    @BeforeEach
    public void setUp() {
        dialog = new Dialog();
    }

    @Test
    public void createDialog() {
        Dialog heroxDialog = new Dialog();
        Assertions.assertEquals(dialog.getTitle(), heroxDialog.getTitle());
        Assertions.assertEquals(dialog.getContent(), heroxDialog.getContent());
        Assertions.assertEquals(dialog.getBase(), heroxDialog.getBase());
    }

    @Test
    public void createCustomDialog() {
        dialog = new Dialog("ASD","");
        Assertions.assertEquals("§fASD", dialog.getTitle().toLegacyText());
        Assertions.assertEquals("§f", dialog.getContent().toLegacyText());
        Assertions.assertEquals("§fASD", dialog.getBase().title().toLegacyText());
    }

    @Test
    public void testSetGetBase() {
        Assertions.assertEquals("§f§6§lDialogo Herox", dialog.getBase().title().toLegacyText());
        dialog.setBase(new DialogBase(new TextComponent("ASD")));
        Assertions.assertEquals("§fASD", dialog.getBase().title().toLegacyText());
    }

    @Test
    public void testSetGetTitle() {
        Assertions.assertEquals("§f§6§lDialogo Herox", dialog.getTitle().toLegacyText());
        dialog.setTitle("ASD");
        Assertions.assertEquals("§fASD", dialog.getTitle().toLegacyText());
    }

    @Test
    public void testSetGetContent() {
        Assertions.assertEquals("§f§7Contenuto di default del dialogo.", dialog.getContent().toLegacyText());
        dialog.setContent("ASD");
        Assertions.assertEquals("§fASD", dialog.getContent().toLegacyText());
    }

    @Test
    public void setTitleRecreatesBase() {
        dialog.setTitle("Nuovo Titolo");
        Assertions.assertEquals("§fNuovo Titolo", dialog.getBase().title().toLegacyText());
    }

    @Test
    public void setContentDoesNotAffectBase() {
        dialog.setContent("Nuovo Contenuto");
        Assertions.assertEquals("§f§6§lDialogo Herox", dialog.getBase().title().toLegacyText());
    }

    @Test
    public void constructorWithNullTitle() {
        Assertions.assertDoesNotThrow(() -> new Dialog(null, "content"));
    }

    @Test
    public void constructorWithNullContent() {
        Assertions.assertDoesNotThrow(() -> new Dialog("title", null));
    }

    @Test
    public void constructorWithBothEmpty() {
        Dialog d = new Dialog("", "");
        Assertions.assertEquals("§f", d.getTitle().toLegacyText());
        Assertions.assertEquals("§f", d.getContent().toLegacyText());
    }

}
