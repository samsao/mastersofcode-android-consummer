# Master hack - OyeOye

## Payment

- Simplify API + SDK: By clicking on buy button, the Simplify CardEditor is shown.
We get the card token and then send it to the our API that will authorize and then collect the payment.

- Masterpass: By clicking on Masterpass, we show a WebView with the Masterpass payment.
We skip the first screen of the <Iframe> because we want to go directly to the Masterpass page.


## Architecture

OyeOye is made with Mortar / Dagger2 / Mortar-Architect / AutoDagger2.
We don't use Fragments here! :)

Remaining architecture is pretty standard: Retrofit, Crashlytics, Digits, Timber, etc.


## NFC BEAM

We use android beam with NFC to exchange data between the consumer and retailer app