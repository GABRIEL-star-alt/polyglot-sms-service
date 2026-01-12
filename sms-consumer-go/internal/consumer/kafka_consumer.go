package consumer

import (
	"context"
	"encoding/json"
	"log"

	"sms-consumer-go/internal/model"
	mongorepot "sms-consumer-go/mongo"

	"github.com/segmentio/kafka-go"
)

type KafkaConsumer struct {
	reader *kafka.Reader
	repo   *mongorepot.Repository
}

func NewKafkaConsumer(
	brokers []string,
	topic string,
	groupID string,
	repo *mongorepot.Repository,
) *KafkaConsumer {

	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers: brokers,
		Topic:   topic,
		GroupID: groupID,
	})

	return &KafkaConsumer{
		reader: reader,
		repo:   repo,
	}
}

func (c *KafkaConsumer) Start(ctx context.Context) {
	log.Println("Kafka consumer started...")

	for {
		msg, err := c.reader.ReadMessage(ctx)
		log.Println("Received messasge:", string(msg.Value))
		if err != nil {
			log.Println("Kafka read error:", err)
			continue
		}

		var event model.SmsEvent
		if err := json.Unmarshal(msg.Value, &event); err != nil {
			log.Println("JSON parse error:", err)
			continue
		}

		if err := c.repo.Save(ctx, event); err != nil {
			log.Println("Mongo insert error:", err)
			continue

		}

		log.Printf("Stored SMS: phone=%s msg=%s status=%s", event.Phone, event.Message, event.Status)
	}
}
