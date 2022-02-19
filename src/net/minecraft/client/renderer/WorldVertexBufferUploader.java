package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;

import net.core.Core;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.SVertexBuilder;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader
{
    @SuppressWarnings("incomplete-switch")
    public void draw(WorldRenderer p_181679_1_)
    {
        if (p_181679_1_.getVertexCount() > 0)
        {
            if (p_181679_1_.getDrawMode() == 7 && Config.isQuadsToTriangles())
            {
                p_181679_1_.quadsToTriangles();
            }

            Minecraft.checkGLError("a");
            GlStateManager.load();
            VertexFormat vertexformat = p_181679_1_.getVertexFormat();
            int i = vertexformat.getNextOffset();
            ByteBuffer bytebuffer = p_181679_1_.getByteBuffer();
            List<VertexFormatElement> list = vertexformat.getElements();
            boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();

            for (int j = 0; j < list.size(); ++j)
            {
                VertexFormatElement vertexformatelement = (VertexFormatElement)list.get(j);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();

                if (flag)
                {
                    Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, new Object[] {vertexformat, Integer.valueOf(j), Integer.valueOf(i), bytebuffer});
                }
                else
                {
                    int k = vertexformatelement.getType().getGlConstant();
                    int l = vertexformatelement.getIndex();
                    bytebuffer.position(vertexformat.getOffset(j));

                    switch (vertexformatelement$enumusage)
                    {
                        case POSITION:
                            if (!Core.CORE) {
                                GL11.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                            }
                            break;

                        case UV:
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
                            if (!Core.CORE) {
                                GL11.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                            }
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;

                        case COLOR:
                            if (!Core.CORE) {
                                GL11.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                            }
                            break;

                        case NORMAL:
                            if (!Core.CORE) {
                                GL11.glNormalPointer(k, i, bytebuffer);
                                GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
                            }
                    }
                }
            }
            Minecraft.checkGLError("a");

            if (p_181679_1_.isMultiTexture())
            {
                p_181679_1_.drawMultiTexture();
                Minecraft.checkGLError("a");
            }
            else if (Config.isShaders())
            {
                SVertexBuilder.drawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount(), p_181679_1_);
                Minecraft.checkGLError("a");
            }
            else
            {
                GlStateManager.load();
                Minecraft.checkGLError("a");
                GL11.glDrawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount());
                Minecraft.checkGLError("a");
            }

            int j1 = 0;

            for (int k1 = list.size(); j1 < k1; ++j1)
            {
                VertexFormatElement vertexformatelement1 = (VertexFormatElement)list.get(j1);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();

                if (flag1)
                {
                    Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, new Object[] {vertexformat, Integer.valueOf(j1), Integer.valueOf(i), bytebuffer});
                }
                else
                {
                    int i1 = vertexformatelement1.getIndex();

                    switch (vertexformatelement$enumusage1)
                    {
                        case POSITION:
                            if (!Core.CORE) {
                                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                            }
                            break;

                        case UV:
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
                            if (!Core.CORE) {
                                GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                            }
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;

                        case COLOR:
                            if (!Core.CORE) {
                                GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                            }
                            GlStateManager.resetColor();
                            break;

                        case NORMAL:
                            if (!Core.CORE) {
                                GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
                            }
                    }
                }
            }
            Minecraft.checkGLError("a");
        }

        p_181679_1_.reset();
    }
}
