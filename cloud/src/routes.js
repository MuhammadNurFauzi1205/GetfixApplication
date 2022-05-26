const {
    addNewOrder,
    getAllOrder,
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
  ];
  
  module.exports = routes;
  