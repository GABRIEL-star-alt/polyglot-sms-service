package main

import (
	"context"
	"log"
	"net/http"

	"sms-consumer-go/internal/config"
	"sms-consumer-go/internal/consumer"
	"sms-consumer-go/internal/handler"

	"sms-consumer-go/internal/service"
	"sms-consumer-go/mongo"
)

func main() {
	cfg := config.Load()

	repo := mongo.NewRepository(
		cfg.MongoURI,
		cfg.MongoDatabase,
		cfg.MongoCollection,
	)

	kafkaConsumer := consumer.NewKafkaConsumer(
		cfg.KafkaBrokers,
		cfg.KafkaTopic,
		cfg.KafkaGroupID,
		repo,
	)

	log.Println("Starting SMS Kafka Consumer")
	go kafkaConsumer.Start(context.Background())
	svc := service.NewSmsService(repo)
	smsHandler := handler.NewHandler(svc)
	router := handler.SetupRoutes(smsHandler)
	log.Println("HTTP server running on :8081")
	log.Fatal(http.ListenAndServe(":8081", router))
}
