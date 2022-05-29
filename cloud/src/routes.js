const {
    addNewOrder,
    getAllOrder,
	getOrderId
  } = require('./handler');
  
  const routes = [
    {
      method: 'POST',
      path: '/Orders',
      handler: addNewOrder,
    },
    {
      method: 'GET',
      path: '/allOrders',
      handler: getAllOrder,
    },
	{
    method: 'GET',
    path: '/Orders/{id}',
    handler: getOrderId,
	},
  ];
  
  module.exports = routes;
  