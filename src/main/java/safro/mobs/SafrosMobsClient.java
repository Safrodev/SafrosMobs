package safro.mobs;

import net.fabricmc.api.ClientModInitializer;
import safro.mobs.client.render.RenderRegistry;

public class SafrosMobsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderRegistry.init();
    }
}
