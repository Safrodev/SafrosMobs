package safro.mobs;

import net.fabricmc.api.ClientModInitializer;
import safro.mobs.client.ClientEvents;
import safro.mobs.client.ParticleRegistry;
import safro.mobs.client.RenderRegistry;

public class SafrosMobsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderRegistry.init();
        ParticleRegistry.init();

        ClientEvents.init();
    }
}
