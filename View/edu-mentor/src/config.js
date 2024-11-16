const backendPort = process.env.PORT || '1234';
const backendUrl = `http://localhost:${backendPort}`;

const config = {
    backendUrl,
};

export default config;
