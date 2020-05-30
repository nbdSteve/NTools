package gg.steve.mc.tp.cmd;

import gg.steve.mc.tp.cmd.misc.HelpSubCmd;
import gg.steve.mc.tp.cmd.misc.ModuleSubCmd;
import gg.steve.mc.tp.cmd.misc.ReloadSubCmd;
import gg.steve.mc.tp.cmd.misc.ToolSubCmd;

public enum SubCommandType {
    HELP_CMD(new HelpSubCmd()),
    TOOL_LIST_CMD(new ModuleSubCmd()),
    MODULE_LIST_CMD(new ToolSubCmd()),
    RELOAD_CMD(new ReloadSubCmd());

    private SubCommand sub;

    SubCommandType(SubCommand sub) {
        this.sub = sub;
    }

    public SubCommand getSub() {
        return sub;
    }
}
