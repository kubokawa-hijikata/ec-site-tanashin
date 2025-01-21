// This is your test secret API key.
const stripe = Stripe("pk_test_51QZg15F056SG56V4roQdj9FTi1rCibJKzFewErYFXtkLAnk4MIIsB0khCFHydhPVoruWD7tUxojmGdrrpg8UVhF3002EyKAv62");

initialize();

// Create a Checkout Session
async function initialize() {
  let totalPrice = document.getElementById("totalPrice").value;
  let formData = new FormData();
    formData.append('totalPrice', totalPrice);
  const fetchClientSecret = async () => {
    const response = await fetch("/create-checkout-session", {
      method: "POST",
      body : formData
    });
    const { clientSecret } = await response.json();
    return clientSecret;
  };

  const checkout = await stripe.initEmbeddedCheckout({
    fetchClientSecret,
  });

  // Mount Checkout
  checkout.mount('#checkout');
}