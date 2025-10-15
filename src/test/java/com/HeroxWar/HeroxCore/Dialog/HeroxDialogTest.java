package com.HeroxWar.HeroxCore.Dialog;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.dialog.DialogBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class HeroxDialogTest {

    private static ServerMock serverMock;

    // Fake Instances
    private PlayerMock playerMock;
    private HeroxDialog dialog = new HeroxDialog();

    @BeforeEach
    public void setUp() {
        // Inizialization server
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void createDialog() {
        HeroxDialog heroxDialog = new HeroxDialog();
        Assertions.assertEquals(dialog.getTitle(), heroxDialog.getTitle());
        Assertions.assertEquals(dialog.getContent(), heroxDialog.getContent());
        Assertions.assertEquals(dialog.getBase(), heroxDialog.getBase());
    }

    @Test
    public void createCustomDialog() {
        dialog = new HeroxDialog("ASD","");
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

}
