const { nanoid } = require('nanoid');
const order = require('./order');

//menambahkan order baru
const addNewOrder = (request, h) => {
    const {
      jenisLayanan, 
      loc, 
      jadwal, 
      detailPekerjaan, 
      desc, 
      image, 
    } = request.payload;

    const id = nanoid(10);
    const insertedAt = new Date().toISOString();
    const updatedAt = insertedAt;
    const newOrder = {
        jenisLayanan, 
        loc, 
        jadwal, 
        detailPekerjaan, 
        desc, 
        image,
        insertedAt,
        updatedAt,
      };

      if (jenisLayanan === undefined) {
        const response = h.response({
          status: 'fail',
          message: 'Gagal membuat pesanan',
        });
        response.code(400);
        return response;
      }
    
      order.push(newOrder);

      const response = h.response({
        status: 'fail',
        message: 'Buku gagal ditambahkan',
      });
      response.code(500);
      return response;
}

//mengambil semua data Order
const getAllOrder = (request, h) => {
  const { username } = request.query;

  if (username !== undefined) {
    const order = orders.filter(
      (order) => order.username.toLowerCase().includes(username.toLowerCase()),
    );

    const response = h.response({
      status: 'success',
      data: {
        orders: order.map((order) => ({
          id: order.id,
          username: order.username
        }),
        ),
      },
    });
    response.code(200);
    return response;
  }

  const response = h.response({
    status: 'success',
    data: {
      orders: orders.map((order) => ({
        id: order.id,
        username: order.username,
      })),
    },
  });
  response.code(200);
  return response;
};

module.exports = {
    addNewOrder,
    getAllOrder,
  };
  