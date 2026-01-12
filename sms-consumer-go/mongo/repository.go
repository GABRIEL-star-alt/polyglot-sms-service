package mongo

import (
	"context"
	"log"
	"time"

	"sms-consumer-go/internal/model"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type SmsRepository interface {
	Save(ctx context.Context, event model.SmsEvent) error
	FindByPhone(ctx context.Context, phone string) ([]model.SmsEvent, error)
}

type Repository struct {
	collection *mongo.Collection
}

func NewRepository(uri, db, collection string) *Repository {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(uri))
	if err != nil {
		log.Fatal("Mongo connect error:", err)
	}

	return &Repository{
		collection: client.Database(db).Collection(collection),
	}
}

func (r *Repository) Save(ctx context.Context, event model.SmsEvent) error {
	_, err := r.collection.InsertOne(ctx, event)
	return err
}
func (r *Repository) FindByPhone(ctx context.Context, phone string) ([]model.SmsEvent, error) {
	filter := bson.M{"phone": phone}

	cursor, err := r.collection.Find(ctx, filter)
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	var results []model.SmsEvent
	for cursor.Next(ctx) {
		var event model.SmsEvent
		if err := cursor.Decode(&event); err != nil {
			return nil, err
		}
		results = append(results, event)
	}

	return results, nil
}
