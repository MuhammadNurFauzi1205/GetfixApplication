const express = require('express');
const Firestore = require('@google-cloud/firestore');
const db = new Firestore();
const { nanoid } = require('nanoid');
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
  const insertedAt = new Date().toISOString();
  const updatedAt = insertedAt;
  let docRef=db.collection('orders')
  await docRef.add({
    id: nanoid(10),
	username : req.body.username,
	userTeknisi : req.body.userTeknisi,
    layanan: req.body.layanan,
    alamat: req.body.alamat, 
    wilayah: req.body.wilayah,
    jadwal: req.body.jadwal,
	waktu : req.body.waktu,
	deskripsi: req.body.deskripsi,
	teknisi: req.body.teknisi,
	insertedAt: req.body.insertedAt,
	updatedAt : req.body.updatedAt
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

//menampilkan semua orderan user berdasarkan id_user
app.get('/allorder/:username', async (req, res) => {
  const username = req.params.username;
  let ord=[]
   const order = await db.collection('orders').where('username', '==', username).get()
  if (order.docs.length > 0) {
    for (const orders of order.docs) {
     ord.push(orders.data())
  }}
  res.json(ord)
})

//mengambil data user by username
app.get('/user/:username', async (req, res) => {
    const username = req.params.username;
    const query = db.collection('user').where('username', '==', username);
    const querySnapshot = await query.get();
    if (querySnapshot.size > 0) {
        res.json(querySnapshot.docs[0].data());
    }
    else {
        res.json({status: 'Not found'});
    }
})

//mengambil data teknisi by username
app.get('/teknisi/:username',  async (req, res) => {
    const username = req.params.username;
    const query = db.collection('teknisi').where('username', '==', username);
    const querySnapshot = await query.get();
    if (querySnapshot.size > 0) {
        res.json(querySnapshot.docs[0].data());
    }
    else {
        res.json({status: 'Not found'});
    }
})