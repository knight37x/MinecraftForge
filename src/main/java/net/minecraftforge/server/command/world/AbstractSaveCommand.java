/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.server.command.world;

import net.minecraft.command.CommandBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

abstract class AbstractSaveCommand extends CommandBase
{

    protected CommandSaveBase base;

    AbstractSaveCommand(CommandSaveBase base)
    {
        this.base = base;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    boolean doesWorldExist(MinecraftServer server, String folderName)
    {
        SaveFormatOld  converter = (SaveFormatOld) server.getActiveAnvilConverter();
        File saveDirectory = new File(converter.savesDirectory, folderName);
        File save = new File(saveDirectory, "level.dat");
        if (exists(server, save))
        {
            return true;
        }
        save = new File(saveDirectory, "level.dat_old");
        return exists(server, save);
    }

    private boolean exists(MinecraftServer server, File save)
    {
        if (!save.exists())
        {
            return false;
        }
        WorldInfo worldInfo = SaveFormatOld.getWorldData(save, server.getDataFixer());
        return worldInfo != null;
    }
}