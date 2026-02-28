package com.cgms.minecraft.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MessageConverter;


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
