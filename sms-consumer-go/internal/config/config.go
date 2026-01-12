package config

import "os"

type Config struct {
	KafkaBrokers []string
	KafkaTopic   string
	KafkaGroupID string

	MongoURI        string
	MongoDatabase   string
	MongoCollection string
}

func Load() Config {
	return Config{
		KafkaBrokers: []string{getEnv("KAFKA_BROKER", "localhost:9092")},
		KafkaTopic:   getEnv("KAFKA_TOPIC", "sms-events"),
		KafkaGroupID: getEnv("KAFKA_GROUP", "sms-consumer-group"),

		MongoURI:        getEnv("MONGO_URI", "mongodb://localhost:27017"),
		MongoDatabase:   getEnv("MONGO_DB", "smsdb"),
		MongoCollection: getEnv("MONGO_COLLECTION", "sms_events"),
	}
}

func getEnv(key, fallback string) string {
	if v := os.Getenv(key); v != "" {
		return v
	}
	return fallback
}
