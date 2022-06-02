const express = require('express');
const Firestore = require('@google-cloud/firestore')
const db = new Firestore();
const app = express();
app.use(express.json());
const port = process.env.PORT || 8080;
app.listen(port, () => {
    console.log(`GetFix Rest API listening on port ${port}`);
});

app.get('/', async (req, res) => {
    res.json({status: 'GetFix is ready.'});
})

//membuat orderan panggil teknisi
app.post('/orders',async (req,res)=>{
  let docRef=db.collection('orders')
  await docRef.add({
    id: req.body.id,
    layanan: req.body.layanan,
    alamat: req.body.alamat,
    wilayah: req.body.wilayah,
    jadwal: req.body.type,
	deskripsi: req.body.deskripsi,
	teknisi: req.body.teknisi
  });
 res.json({message:'order success'});
})

//menampilkan data orderan berdasarkan id
app.get('/orders/:id', async (req, res) => {
    const id = req.params.id;
    const query = db.collection('orders').where('id', '==', id);
    const querySnapshot = await query.get();
    if (querySnapshot.size > 0) {
        res.json(querySnapshot.docs[0].data());
    }
    else {
        res.json({status: 'Not found'});
    }
})

//menampilkan semua orderan
app.get('/allorder', async (req, res) => {
  let ord=[]
   const oreder = await db.collection('orders').get()
  if (user.docs.length > 0) {
    for (const orders of orders.docs) {
     ord.push(orders.data())
  }}
  res.json(ord)
})
