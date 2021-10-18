package com.kanyideveloper.savingszetu.utils

object SweetAlert {

/*    loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
    loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
    loading.setTitleText("Loading...");
    loading.setCancelable(false);

    success = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
    success.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
    success.setTitleText("Successfully requested for garbage collection");
    success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            success.dismiss();
        }
    });
    success.setCancelable(false);*/


    /*
                    new SweetAlertDialog(WasteActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure you want to request garbage collection?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                loading.show();

                                if (paymentType.equals("Cash")) {
                                    pushWasteDetailsToFirebase(false, phone);
                                } else if (paymentType.equals("M-Pesa")) {
                                    loading.show();
                                    if (wasteType == null) {
                                        Toast.makeText(WasteActivity.this, "You must select Waste Type", Toast.LENGTH_SHORT).show();
                                    } else if (paymentType == null) {
                                        Toast.makeText(WasteActivity.this, "PaymentRepository Type is compulsory", Toast.LENGTH_SHORT).show();
                                    }

                                    phoneNum = binding.phoneNumEdtx.getText().toString();
                                    if (binding.phoneNumEdtx.getText().toString().isEmpty()) {
                                        binding.phoneNumEdtx.setError("Phone Number is required");
                                        return;
                                    }
                                    LNMExpress lnmExpress = new LNMExpress(
                                            "174379",
                                            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                                            TransactionType.CustomerPayBillOnline,
                                            "1",
                                            "254708374149",
                                            "174379",
                                            phoneNum,
                                            "https://usafi-48370-default-rtdb.firebaseio.com/",
                                            "001ABC",
                                            "Paybill option"
                                    );

                                    daraja.requestMPESAExpress(lnmExpress,
                                            new DarajaListener<LNMResult>() {
                                                @Override
                                                public void onResult(@NonNull LNMResult lnmResult) {
                                                    Log.i(WasteActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                                                    pushWasteDetailsToFirebase(true, phoneNum);
                                                    success.dismissWithAnimation();
                                                    loading.dismissWithAnimation();
                                                    binding.phoneNumEdtx.setText("");
                                                    binding.phoneNumEdtx.setVisibility(View.GONE);
                                                }

                                                @Override
                                                public void onError(String error) {
                                                    Log.i(WasteActivity.this.getClass().getSimpleName(), error);
                                                }
                                            }
                                    );
                                }
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
    * */
}