db.createUser(
    {
      user: 'api_user',
      pwd: 'secret',
      roles: [{ role: 'readWrite', db: 'massive_transport' }],
    },
  );
  db.createCollection('transactions');
  db.createCollection('daily-sumary');