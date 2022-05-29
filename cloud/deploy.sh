GOOGLE_PROJECT_ID=

gcloud builds submit --tag gcr.io/$GOOGLE_PROJECT_ID/getfixapi\
    --project=$GOOGLE_PROJECT_ID

gcloud beta run deploy getfix-api \
    --image gcr.io/$GOOGLE_PROJECT_ID/getfixapi \
    --platform managed \
    --region asia-southeast2 \
    --project=$GOOGLE_PROJECT_ID