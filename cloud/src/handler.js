const { nanoid } = require('nanoid');
const order = require('./orders');

//menambahkan order baru
const addNewOrder = (request, h) => {
    const {
      jenisLayanan, 
      loc, 
      jadwal, 
      detailPekerjaan, 
      desc, 
    } = request.payload;

    const id = nanoid(10);
    const insertedAt = new Date().toISOString();
    const updatedAt = insertedAt;
    const newOrder = {
		id,
        jenisLayanan, 
        loc, 
        jadwal, 
        detailPekerjaan, 
        desc,
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
	  
	  const isSuccess = order.filter((order) => order.id === id).length > 0;

	  if (isSuccess) {
		const response = h.response({
		  status: 'success',
		  message: 'pesanan telah dibuat',
		  data: {
			orderId: id,
		  },
		});
		response.code(201);
		return response;
	  }

      const response = h.response({
        status: 'fail'
      });
      response.code(500);
      return response;
}

//mengambil semua data Order
const getAllOrder = (request, h) => {
  const { username } = request.query;

  if (username !== undefined) {
    const order = order.filter(
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
      order: order.map((order) => ({
        id: order.id,
        username: order.username,
      })),
    },
  });
  response.code(200);
  return response;
};

//mengambil data pesanan dari idpesanan
const getOrderId = (request, h) => {
  const {id} = request.params;
  const orders = order.filter((n) => n.id === id)[0];

  if (order !== undefined) {
    return {
      status: 'success',
      data: {
        orders,
      },
    };
  }

  const response = h.response({
    status: 'fail',
    message: 'Orderan Tidak Ditemukan',
  });
  response.code(404);
  return response;
};

module.exports = {
    addNewOrder,
    getAllOrder,
	getOrderId
  };
  