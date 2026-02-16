package com.cgms.minecraft.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
@EnableJms
public class MinecraftAiApp
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MinecraftAiApp.class );

    public static void main(String[] args)
    {
        LOGGER.info("STARTING APPLICATION: Minecraft Empires AI");
        SpringApplication.run( MinecraftAiApp.class, args );
    }
}
