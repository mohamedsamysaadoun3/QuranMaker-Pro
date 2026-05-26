package hazem.nurmontage.videoquran;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import hazem.nurmontage.videoquran.SupportBillingActivity;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.NonScrollableLinearLayoutManager;
import hazem.nurmontage.videoquran.Utils.PriceFormatter;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.AboutAdabters;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SupportBillingActivity extends Base implements PurchasesUpdatedListener {
    private static String PRODUCT_ID_10$ = "sku.nurmontage.min";
    private static String PRODUCT_ID_100$ = "sku.nurmontage.mmedium";
    private static String PRODUCT_ID_1000$ = "sku.nurmontage.max";
    private static String PRODUCT_ID_50$ = "sku.nurmontage.medium";
    private static String product_id_current = "sku.nurmontage.medium";
    private BillingClient billingClient;
    private ButtonCustumFont btn_launch;
    private Resources mResources;
    private ButtonCustumFont view_price_10$;
    private ButtonCustumFont view_price_100$;
    private ButtonCustumFont view_price_1000$;
    private ButtonCustumFont view_price_50$;
    private int price_select = R.id.view_50;
    private Map<String, ProductDetails> productDetailsMap = new HashMap();
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.SupportBillingActivity.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            SupportBillingActivity.this.finish();
        }
    };

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_support_billing);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda6
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return SupportBillingActivity.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        setStatusBarColor(-1);
        setNavigationBarColor(-1);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        insetsController.setAppearanceLightNavigationBars(true);
        wakeLockAquire();
        Resources resources = getResources();
        this.mResources = resources;
        if (resources == null) {
            finish();
        }
        init();
        initImgBilling();
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SupportBillingActivity.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        this.billingClient = BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build();
        startBillingConnection();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void initImgBilling() {
        ScreenUtils.getScreenWidth(this);
    }

    private void init() {
        String language = LocaleHelper.getLanguage(getApplicationContext());
        TextCustumFont textCustumFont = (TextCustumFont) findViewById(R.id.tv_aya);
        textCustumFont.setText(this.mResources.getString(R.string.tittle_billing));
        if (language.equals("ar")) {
            textCustumFont.setTextSize(2, 16.0f);
        }
        ArrayList arrayList = new ArrayList();
        int i = language.equals("ar") ? 5 : GravityCompat.START;
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#000000'>" + this.mResources.getString(R.string.about_question_1) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#000000'>" + this.mResources.getString(R.string.about_question_2) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#000000'>" + this.mResources.getString(R.string.about_question_3) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#000000'>" + this.mResources.getString(R.string.about_no_ads) + "</font>", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("\n", Integer.valueOf(i))));
        arrayList.add(new AboutAdabters.ModelAbout(14, (Pair<String, Integer>) new Pair("<font color='#000000'>" + this.mResources.getString(R.string.about_cost_explanation) + "</font>", Integer.valueOf(i))));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new NonScrollableLinearLayoutManager(this));
        recyclerView.setAdapter(new AboutAdabters(this, AppUtils.getAppVersionName(this), arrayList, ScreenUtils.getScreenWidth(this), (int) (ScreenUtils.getScreenWidth(this) * 0.4f)));
        this.view_price_10$ = (ButtonCustumFont) findViewById(R.id.view_10);
        this.view_price_50$ = (ButtonCustumFont) findViewById(R.id.view_50);
        this.view_price_100$ = (ButtonCustumFont) findViewById(R.id.view_100);
        this.view_price_1000$ = (ButtonCustumFont) findViewById(R.id.view_1000);
        this.btn_launch = (ButtonCustumFont) findViewById(R.id.btn_launch);
        this.view_price_10$.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SupportBillingActivity.this.m886lambda$init$1$hazemnurmontagevideoquranSupportBillingActivity(view);
            }
        });
        this.view_price_50$.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SupportBillingActivity.this.m887lambda$init$2$hazemnurmontagevideoquranSupportBillingActivity(view);
            }
        });
        this.view_price_100$.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SupportBillingActivity.this.m888lambda$init$3$hazemnurmontagevideoquranSupportBillingActivity(view);
            }
        });
        this.view_price_1000$.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SupportBillingActivity.this.m889lambda$init$4$hazemnurmontagevideoquranSupportBillingActivity(view);
            }
        });
        this.btn_launch.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SupportBillingActivity.this.launchPurchaseFlow(SupportBillingActivity.product_id_current);
            }
        });
    }

    /* renamed from: lambda$init$1$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m886lambda$init$1$hazemnurmontagevideoquranSupportBillingActivity(View view) {
        product_id_current = PRODUCT_ID_10$;
        updatePrice(this.view_price_10$.getText().toString(), R.id.view_10, this.price_select);
    }

    /* renamed from: lambda$init$2$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m887lambda$init$2$hazemnurmontagevideoquranSupportBillingActivity(View view) {
        product_id_current = PRODUCT_ID_50$;
        updatePrice(this.view_price_50$.getText().toString(), R.id.view_50, this.price_select);
    }

    /* renamed from: lambda$init$3$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m888lambda$init$3$hazemnurmontagevideoquranSupportBillingActivity(View view) {
        product_id_current = PRODUCT_ID_100$;
        updatePrice(this.view_price_100$.getText().toString(), R.id.view_100, this.price_select);
    }

    /* renamed from: lambda$init$4$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m889lambda$init$4$hazemnurmontagevideoquranSupportBillingActivity(View view) {
        product_id_current = PRODUCT_ID_1000$;
        updatePrice(this.view_price_1000$.getText().toString(), R.id.view_1000, this.price_select);
    }

    private void updatePrice(String str, int i, int i2) {
        if (i == i2) {
            return;
        }
        ButtonCustumFont buttonCustumFont = this.btn_launch;
        if (buttonCustumFont != null) {
            buttonCustumFont.setText(String.format(this.mResources.getString(R.string.btn_launch_billing), str));
        }
        findViewById(i).setBackgroundResource(R.drawable.item_billing_select);
        findViewById(i2).setBackgroundResource(R.drawable.item_billing);
        this.price_select = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBillingConnection() {
        this.billingClient.startConnection(new BillingClientStateListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity.4
            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == 0) {
                    SupportBillingActivity.this.queryProducts();
                    SupportBillingActivity.this.queryPurchases();
                }
            }

            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingServiceDisconnected() {
                SupportBillingActivity.this.startBillingConnection();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryProducts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_10$).setProductType("inapp").build());
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_50$).setProductType("inapp").build());
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_100$).setProductType("inapp").build());
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_1000$).setProductType("inapp").build());
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new AnonymousClass5());
    }

    /* renamed from: hazem.nurmontage.videoquran.SupportBillingActivity$5, reason: invalid class name */
    class AnonymousClass5 implements ProductDetailsResponseListener {
        AnonymousClass5() {
        }

        @Override // com.android.billingclient.api.ProductDetailsResponseListener
        public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
            if (billingResult.getResponseCode() != 0 || list == null) {
                return;
            }
            for (final ProductDetails productDetails : list) {
                SupportBillingActivity.this.productDetailsMap.put(productDetails.getProductId(), productDetails);
                SupportBillingActivity.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SupportBillingActivity.AnonymousClass5.this.m891x1ed83fa4(productDetails);
                    }
                });
            }
        }

        /* renamed from: lambda$onProductDetailsResponse$0$hazem-nurmontage-videoquran-SupportBillingActivity$5, reason: not valid java name */
        /* synthetic */ void m891x1ed83fa4(ProductDetails productDetails) {
            SupportBillingActivity.this.updateUI(productDetails);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda1
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                SupportBillingActivity.this.m890x62977897(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$queryPurchases$5$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m890x62977897(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                handlePurchase((Purchase) it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(ProductDetails productDetails) {
        String productId = productDetails.getProductId();
        String formatPrice = PriceFormatter.formatPrice(productDetails.getOneTimePurchaseOfferDetails().getFormattedPrice());
        if (productId.equals(PRODUCT_ID_10$)) {
            this.view_price_10$.setText(formatPrice);
            return;
        }
        if (productId.equals(PRODUCT_ID_50$)) {
            this.view_price_50$.setText(formatPrice);
            this.btn_launch.setText(String.format(this.mResources.getString(R.string.btn_launch_billing), this.view_price_50$.getText().toString()));
        } else if (productId.equals(PRODUCT_ID_100$)) {
            this.view_price_100$.setText(formatPrice);
        } else if (productId.equals(PRODUCT_ID_1000$)) {
            this.view_price_1000$.setText(formatPrice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchPurchaseFlow(String str) {
        ProductDetails productDetails = this.productDetailsMap.get(str);
        if (productDetails == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build());
        this.billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).build());
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        if (billingResult.getResponseCode() == 0 && list != null) {
            Iterator<Purchase> it = list.iterator();
            while (it.hasNext()) {
                handlePurchase(it.next());
            }
            return;
        }
        billingResult.getResponseCode();
    }

    private void thnks() {
        Intent intent = new Intent(this, (Class<?>) ThanksYouActivity.class);
        intent.putExtra("price", "" + ((ButtonCustumFont) findViewById(this.price_select)).getText().toString());
        startActivity(intent);
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == 1) {
            thnks();
            if (purchase.isAcknowledged()) {
                return;
            }
            this.billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), new ConsumeResponseListener() { // from class: hazem.nurmontage.videoquran.SupportBillingActivity$$ExternalSyntheticLambda0
                @Override // com.android.billingclient.api.ConsumeResponseListener
                public final void onConsumeResponse(BillingResult billingResult, String str) {
                    SupportBillingActivity.this.m885x47956cb7(billingResult, str);
                }
            });
            return;
        }
        if (purchase.getPurchaseState() == 2) {
            Toast.makeText(this, "Purchase is pending", 0).show();
        } else {
            Toast.makeText(this, "Purchase is in unknown state", 0).show();
        }
    }

    /* renamed from: lambda$handlePurchase$6$hazem-nurmontage-videoquran-SupportBillingActivity, reason: not valid java name */
    /* synthetic */ void m885x47956cb7(BillingResult billingResult, String str) {
        if (billingResult.getResponseCode() == 0) {
            Toast.makeText(this, "Purchase consumed successfully", 0).show();
        } else {
            Toast.makeText(this, "Error consuming purchase", 0).show();
        }
    }
}
