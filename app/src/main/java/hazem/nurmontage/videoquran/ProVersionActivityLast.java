package hazem.nurmontage.videoquran;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;

import hazem.nurmontage.videoquran.ProVersionActivityLast;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyVibrationHelper;
import hazem.nurmontage.videoquran.Utils.NetworkUtils;
import hazem.nurmontage.videoquran.adabter.FeaturesAdabter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.fragment.ProgressViewFragment;
import hazem.nurmontage.videoquran.model.ModelFeatures;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.views.ButtonCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.xml.image.ImageUtil;

/* loaded from: classes2.dex */
public class ProVersionActivityLast extends Base implements PurchasesUpdatedListener {
    private static String PRODUCT_ID_FOREIVER = "sku.nurmontage.foreiver";
    private static String PRODUCT_ID_MONTH = "sku.nurmontage.month";
    private static String PRODUCT_ID_YEAR = "sku.nurmontage.year";
    private BillingClient billingClient;
    private ButtonCustumFont btnContinue;
    private RelativeLayout btn_foreiver;
    private RelativeLayout btn_month;
    private TextCustumFont btn_restore;
    private RelativeLayout btn_year;
    private FeaturesAdabter featuresAdabter;
    boolean hasPurchasedForever;
    private boolean isClick;
    private boolean isrESTORE;
    private ImageView ivForeiver;
    private ImageView ivMonth;
    private ImageView ivYear;
    private Resources mResources;
    private Template mTemplate;
    private TextCustumFont tv_foreiver;
    private TextCustumFont tv_month;
    private TextCustumFont tv_price_foreiver;
    private TextCustumFont tv_price_month;
    private TextCustumFont tv_price_year;
    private TextCustumFont tv_year;
    private int color_select = -206036;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if (ProVersionActivityLast.this.mTemplate != null) {
                ProVersionActivityLast.this.toTrackAct();
            }
            ProVersionActivityLast.this.finish();
        }
    };
    private Map<String, ProductDetails> productDetailsMap = new HashMap();
    private String product_id_current = PRODUCT_ID_YEAR;

    /* JADX INFO: Access modifiers changed from: private */
    public void toTrackAct() {
        Intent intent = new Intent(this, (Class<?>) EngineActivity.class);
        Template template = this.mTemplate;
        if (template != null) {
            intent.putExtra(Common.TEMPLATE, template.getIdTemplate());
        }
        intent.addFlags(65536);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.onAttach(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        String stringExtra;
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pro_version);
        setStatusBarColor(-15658732);
        setNavigationBarColor(-15658732);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$$ExternalSyntheticLambda2
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ProVersionActivityLast.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Resources resources = getResources();
        this.mResources = resources;
        if (resources == null) {
            finish();
        }
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, this.mResources.getString(R.string.no_internet), 0).show();
        }
        wakeLockAquire();
        if (getIntent() != null && (stringExtra = getIntent().getStringExtra(Common.TEMPLATE)) != null) {
            this.mTemplate = (Template) LocalPersistence.readObjectFromFile(this, stringExtra);
        }
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProVersionActivityLast.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        boolean isSubscribed = BillingPreferences.isSubscribed(getApplicationContext());
        initImgFeatures();
        initBtnHelp();
        if (isSubscribed) {
            thnks();
            return;
        }
        this.billingClient = BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build();
        startBillingConnection();
        this.btn_restore.setText(this.mResources.getString(R.string.restort_subscribe));
        this.btn_restore.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (ProVersionActivityLast.this.billingClient != null && ProVersionActivityLast.this.billingClient.isReady()) {
                        ProVersionActivityLast.this.showProgress();
                        ProVersionActivityLast.this.checkUserSubscriptionStatus();
                    } else {
                        ProVersionActivityLast.this.startBillingConnection();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.tv_foreiver = (TextCustumFont) findViewById(R.id.tv_foreiver);
        this.tv_price_year = (TextCustumFont) findViewById(R.id.tv_price_year);
        this.tv_price_foreiver = (TextCustumFont) findViewById(R.id.tv_price_foreiver);
        this.btn_foreiver = (RelativeLayout) findViewById(R.id.btn_foreiver);
        this.btn_year = (RelativeLayout) findViewById(R.id.btn_year);
        this.ivForeiver = (ImageView) findViewById(R.id.btn_radio_foreiver);
        this.ivYear = (ImageView) findViewById(R.id.btn_radio_year);
        this.btn_foreiver.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Objects.equals(ProVersionActivityLast.this.product_id_current, ProVersionActivityLast.PRODUCT_ID_FOREIVER)) {
                    return;
                }
                ProVersionActivityLast.this.btn_foreiver.setBackgroundResource(R.drawable.bg_price_select);
                ProVersionActivityLast.this.btn_year.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.btn_month.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.ivForeiver.setImageResource(R.drawable.checked);
                ProVersionActivityLast.this.tv_foreiver.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_price_foreiver.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_year.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_year.setTextColor(-1);
                ProVersionActivityLast.this.tv_month.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_month.setTextColor(-1);
                ProVersionActivityLast.this.ivYear.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.ivMonth.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.product_id_current = ProVersionActivityLast.PRODUCT_ID_FOREIVER;
            }
        });
        this.btn_year.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Objects.equals(ProVersionActivityLast.this.product_id_current, ProVersionActivityLast.PRODUCT_ID_YEAR)) {
                    return;
                }
                ProVersionActivityLast.this.btn_year.setBackgroundResource(R.drawable.bg_price_select);
                ProVersionActivityLast.this.btn_foreiver.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.btn_month.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.product_id_current = ProVersionActivityLast.PRODUCT_ID_YEAR;
                ProVersionActivityLast.this.ivYear.setImageResource(R.drawable.checked);
                ProVersionActivityLast.this.ivForeiver.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.ivMonth.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.tv_year.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_price_year.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_foreiver.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_foreiver.setTextColor(-1);
                ProVersionActivityLast.this.tv_month.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_month.setTextColor(-1);
            }
        });
        this.btn_month.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Objects.equals(ProVersionActivityLast.this.product_id_current, ProVersionActivityLast.PRODUCT_ID_MONTH)) {
                    return;
                }
                ProVersionActivityLast.this.btn_month.setBackgroundResource(R.drawable.bg_price_select);
                ProVersionActivityLast.this.btn_foreiver.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.btn_year.setBackgroundResource(R.drawable.bg_price);
                ProVersionActivityLast.this.product_id_current = ProVersionActivityLast.PRODUCT_ID_MONTH;
                ProVersionActivityLast.this.ivMonth.setImageResource(R.drawable.checked);
                ProVersionActivityLast.this.ivYear.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.ivForeiver.setImageResource(R.drawable.unchecked);
                ProVersionActivityLast.this.tv_month.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_price_month.setTextColor(ProVersionActivityLast.this.color_select);
                ProVersionActivityLast.this.tv_year.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_year.setTextColor(-1);
                ProVersionActivityLast.this.tv_foreiver.setTextColor(-1);
                ProVersionActivityLast.this.tv_price_foreiver.setTextColor(-1);
            }
        });
        ButtonCustumFont buttonCustumFont = (ButtonCustumFont) findViewById(R.id.btn_continue);
        this.btnContinue = buttonCustumFont;
        buttonCustumFont.setText(this.mResources.getString(R.string.subscribe_now));
        this.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (ProVersionActivityLast.this.isClick) {
                        return;
                    }
                    ProVersionActivityLast.this.isClick = true;
                    ProVersionActivityLast.this.showProgress();
                    ProductDetails productDetails = (ProductDetails) ProVersionActivityLast.this.productDetailsMap.get(ProVersionActivityLast.this.product_id_current);
                    if (productDetails == null) {
                        return;
                    }
                    if (ProVersionActivityLast.this.product_id_current.equals(ProVersionActivityLast.PRODUCT_ID_FOREIVER)) {
                        ProVersionActivityLast.this.launchPurchaseFlowINAPP(productDetails);
                    } else {
                        ProVersionActivityLast.this.launchPurchaseFlowSUB(productDetails);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void initBtnHelp() {
        findViewById(R.id.btn_contact).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProVersionActivityLast.this.contact();
            }
        });
    }

    private boolean isGmailAvailable(Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        return !context.getPackageManager().queryIntentActivities(intent, 0).isEmpty();
    }

    public void contact() {
        String string = this.mResources.getString(R.string.support_team);
        if (BillingPreferences.isSubscribed(this)) {
            string = string + " : ";
        }
        String[] strArr = {"hazemourari08@gmail.com"};
        if (isGmailAvailable(this)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.EMAIL", strArr);
            intent.putExtra("android.intent.extra.BCC", strArr);
            intent.putExtra("android.intent.extra.SUBJECT", string);
            intent.setType("message/rfc822");
            intent.setPackage("com.google.android.gm");
            try {
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.putExtra("android.intent.extra.EMAIL", strArr);
            intent2.putExtra("android.intent.extra.BCC", strArr);
            intent2.putExtra("android.intent.extra.SUBJECT", string);
            intent2.setType("message/rfc822");
            startActivity(Intent.createChooser(intent2, "Send email using"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initImgFeatures() {
        findViewById(R.id.ytb_layout).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("vnd.youtube:3xtsWfMQ5KM"));
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/3xtsWfMQ5KM"));
                try {
                    ProVersionActivityLast.this.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    ProVersionActivityLast.this.startActivity(intent2);
                }
            }
        });
        if (LocaleHelper.getLanguage(this).equals("ar")) {
            TextCustumFont textCustumFont = (TextCustumFont) findViewById(R.id.tv_pro_ar);
            textCustumFont.setVisibility(0);
            textCustumFont.setText(this.mResources.getString(R.string.pro));
            TextCustumFont textCustumFont2 = (TextCustumFont) findViewById(R.id.tv_free_ar);
            textCustumFont2.setVisibility(0);
            textCustumFont2.setText(this.mResources.getString(R.string.free));
            findViewById(R.id.tv_pro).setVisibility(8);
        } else {
            TextCustumFont textCustumFont3 = (TextCustumFont) findViewById(R.id.tv_pro);
            textCustumFont3.setVisibility(0);
            textCustumFont3.setText(this.mResources.getString(R.string.pro));
            TextCustumFont textCustumFont4 = (TextCustumFont) findViewById(R.id.tv_free);
            textCustumFont4.setVisibility(0);
            textCustumFont4.setText(this.mResources.getString(R.string.free));
        }
        ((TextCustumFont) findViewById(R.id.tv_tittle_billing)).setText(this.mResources.getString(R.string.unlock_premium));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList arrayList = new ArrayList();
        String[] stringArray = this.mResources.getStringArray(R.array.feature_list);
        for (int i = 0; i < stringArray.length; i++) {
            if (i == 0 || i == 1) {
                arrayList.add(new ModelFeatures(stringArray[i], true));
            } else {
                arrayList.add(new ModelFeatures(stringArray[i]));
            }
        }
        FeaturesAdabter featuresAdabter = new FeaturesAdabter(arrayList);
        this.featuresAdabter = featuresAdabter;
        recyclerView.setAdapter(featuresAdabter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBillingConnection() {
        this.billingClient.startConnection(new BillingClientStateListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.10
            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == 0) {
                    ProVersionActivityLast.this.checkUserSubscriptionStatus();
                    ProVersionActivityLast.this.queryProducts();
                    ProVersionActivityLast.this.querySubscribe();
                    ProVersionActivityLast.this.queryPurchases();
                }
            }

            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingServiceDisconnected() {
                ProVersionActivityLast.this.startBillingConnection();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryProducts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_FOREIVER).setProductType("inapp").build());
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new AnonymousClass11());
    }

    /* renamed from: hazem.nurmontage.videoquran.ProVersionActivityLast$11, reason: invalid class name */
    class AnonymousClass11 implements ProductDetailsResponseListener {
        AnonymousClass11() {
        }

        @Override // com.android.billingclient.api.ProductDetailsResponseListener
        public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
            if (billingResult.getResponseCode() != 0 || list == null) {
                return;
            }
            for (final ProductDetails productDetails : list) {
                ProVersionActivityLast.this.productDetailsMap.put(productDetails.getProductId(), productDetails);
                ProVersionActivityLast.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$11$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProVersionActivityLast.AnonymousClass11.this.m787x289777fc(productDetails);
                    }
                });
            }
        }

        /* renamed from: lambda$onProductDetailsResponse$0$hazem-nurmontage-videoquran-ProVersionActivityLast$11, reason: not valid java name */
        /* synthetic */ void m787x289777fc(ProductDetails productDetails) {
            ProVersionActivityLast.this.updateUI(productDetails);
        }
    }

    private void queryUserPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$$ExternalSyntheticLambda3
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityLast.this.m786x60a554bb(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$queryUserPurchases$1$hazem-nurmontage-videoquran-ProVersionActivityLast, reason: not valid java name */
    /* synthetic */ void m786x60a554bb(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0) {
            this.hasPurchasedForever = false;
            if (list != null) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Purchase purchase = (Purchase) it.next();
                    if (purchase.getProducts().contains(PRODUCT_ID_FOREIVER) && purchase.getPurchaseState() == 1) {
                        this.hasPurchasedForever = true;
                        break;
                    }
                }
            }
        } else {
            Log.w("Billing", "Error querying INAPP purchases: " + billingResult.getDebugMessage());
        }
        Log.e("hasPurchasedForever", "" + this.hasPurchasedForever);
        checkInAppPurchases();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void querySubscribe() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_YEAR).setProductType("subs").build());
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_MONTH).setProductType("subs").build());
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new AnonymousClass12());
    }

    /* renamed from: hazem.nurmontage.videoquran.ProVersionActivityLast$12, reason: invalid class name */
    class AnonymousClass12 implements ProductDetailsResponseListener {
        AnonymousClass12() {
        }

        @Override // com.android.billingclient.api.ProductDetailsResponseListener
        public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
            if (billingResult.getResponseCode() != 0 || list == null) {
                return;
            }
            for (final ProductDetails productDetails : list) {
                ProVersionActivityLast.this.productDetailsMap.put(productDetails.getProductId(), productDetails);
                ProVersionActivityLast.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$12$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProVersionActivityLast.AnonymousClass12.this.m788x289777fd(productDetails);
                    }
                });
            }
        }

        /* renamed from: lambda$onProductDetailsResponse$0$hazem-nurmontage-videoquran-ProVersionActivityLast$12, reason: not valid java name */
        /* synthetic */ void m788x289777fd(ProductDetails productDetails) {
            ProVersionActivityLast.this.updateUI(productDetails);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(ProductDetails productDetails) {
        String priceStr = null;
        if (productDetails.getProductType().equals("inapp")) {
            priceStr = productDetails.getOneTimePurchaseOfferDetails() != null ? productDetails.getOneTimePurchaseOfferDetails().getFormattedPrice() : null;
            this.tv_price_foreiver.setText(priceStr != null ? priceStr : "N/A");
            return;
        }
        if (productDetails.getProductType().equals("subs")) {
            if (productDetails.getSubscriptionOfferDetails() != null && !productDetails.getSubscriptionOfferDetails().isEmpty()) {
                ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails = productDetails.getSubscriptionOfferDetails().get(0);
                if (subscriptionOfferDetails.getPricingPhases() != null && !subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().isEmpty()) {
                    priceStr = subscriptionOfferDetails.getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice();
                }
            }
            String productId = productDetails.getProductId();
            if (PRODUCT_ID_YEAR.equals(productId)) {
                this.tv_price_year.setText(priceStr != null ? priceStr : "N/A");
            } else if (PRODUCT_ID_MONTH.equals(productId)) {
                this.tv_price_month.setText(priceStr != null ? priceStr : "N/A");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$$ExternalSyntheticLambda1
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityLast.this.m785x780395ef(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$queryPurchases$2$hazem-nurmontage-videoquran-ProVersionActivityLast, reason: not valid java name */
    /* synthetic */ void m785x780395ef(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                handlePurchase((Purchase) it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgress() {
        try {
            findViewById(R.id.container_progress).setVisibility(0);
            if (isFinishing() || getSupportFragmentManager().isDestroyed()) {
                return;
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.container_progress, ProgressViewFragment.getInstance());
            beginTransaction.commit();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideProgressFragment() {
        try {
            this.isClick = false;
            if (!isFinishing() && !getSupportFragmentManager().isDestroyed()) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.container_progress);
                if (findFragmentById != null) {
                    beginTransaction.remove(findFragmentById);
                }
                beginTransaction.commit();
            }
        } catch (Exception unused) {
        }
        findViewById(R.id.container_progress).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchPurchaseFlowINAPP(ProductDetails productDetails) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build());
        this.billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchPurchaseFlowSUB(ProductDetails productDetails) {
        String findOfferToken = findOfferToken(productDetails);
        if (findOfferToken == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).setOfferToken(findOfferToken).build());
        this.billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).build());
    }

    private String findOfferToken(ProductDetails productDetails) {
        List<ProductDetails.SubscriptionOfferDetails> subscriptionOfferDetails = productDetails.getSubscriptionOfferDetails();
        if (subscriptionOfferDetails == null || subscriptionOfferDetails.isEmpty()) {
            return null;
        }
        return subscriptionOfferDetails.get(0).getOfferToken();
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        hideProgressFragment();
        if (billingResult.getResponseCode() == 0 && list != null) {
            Iterator<Purchase> it = list.iterator();
            while (it.hasNext()) {
                handlePurchase(it.next());
            }
            return;
        }
        billingResult.getResponseCode();
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == 1) {
            if (purchase.isAcknowledged()) {
                return;
            }
            this.billingClient.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), new AcknowledgePurchaseResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$$ExternalSyntheticLambda0
                @Override // com.android.billingclient.api.AcknowledgePurchaseResponseListener
                public final void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    ProVersionActivityLast.lambda$handlePurchase$3(billingResult);
                }
            });
            BillingPreferences.saveSubscriptionStatus(getApplicationContext(), true);
            playVibration();
            thnks();
            return;
        }
        if (purchase.getPurchaseState() == 2) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.13
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(ProVersionActivityLast.this.getApplicationContext(), "Purchase is pending", 0).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.14
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(ProVersionActivityLast.this.getApplicationContext(), "Purchase is in unknown state", 0).show();
                }
            });
        }
    }

    static /* synthetic */ void lambda$handlePurchase$3(BillingResult billingResult) {
        if (billingResult.getResponseCode() == 0) {
            Log.e("Billing", PRODUCT_ID_FOREIVER + " acknowledged.");
        } else {
            Log.e("Billing", "Failed to acknowledge " + PRODUCT_ID_FOREIVER + ": " + billingResult.getDebugMessage());
        }
    }

    public void explode() {
        Shape.DrawableShape loadDrawable = ImageUtil.loadDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_24px), true, true);
        KonfettiView konfettiView = (KonfettiView) findViewById(R.id.konfettiView);
        konfettiView.setVisibility(0);
        konfettiView.start(new PartyFactory(new Emitter(2800L, TimeUnit.MILLISECONDS).max(100)).spread(Spread.ROUND).shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, loadDrawable)).colors(Arrays.asList(16572810, 16740973, 16003181, 11832815)).setSpeedBetween(0.0f, 30.0f).position(new Position.Relative(0.5d, 0.3d)).build());
    }

    private void thnks() {
        try {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.15
                @Override // java.lang.Runnable
                public void run() {
                    ProVersionActivityLast.this.findViewById(R.id.restore).setVisibility(8);
                    ProVersionActivityLast.this.findViewById(R.id.layout_price).setVisibility(8);
                    ProVersionActivityLast.this.findViewById(R.id.view_success).setVisibility(0);
                    ((TextCustumFont) ProVersionActivityLast.this.findViewById(R.id.tv_thanks)).setText(ProVersionActivityLast.this.mResources.getString(R.string.thanks_hint));
                    ProVersionActivityLast.this.findViewById(R.id.tv_tittle_billing).setVisibility(8);
                    ProVersionActivityLast proVersionActivityLast = ProVersionActivityLast.this;
                    proVersionActivityLast.btnContinue = (ButtonCustumFont) proVersionActivityLast.findViewById(R.id.btn_done);
                    ProVersionActivityLast.this.btnContinue.setText(ProVersionActivityLast.this.mResources.getString(R.string.done));
                    ProVersionActivityLast.this.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.15.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ProVersionActivityLast.this.onBackPressedCallback.handleOnBackPressed();
                        }
                    });
                    ProVersionActivityLast.this.featuresAdabter.setSubscribe(true);
                    ProVersionActivityLast.this.explode();
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUserSubscriptionStatus() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("subs").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.16
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
                if (billingResult.getResponseCode() == 0) {
                    if (!list.isEmpty()) {
                        ProVersionActivityLast.this.handleSubscriptionPurchases(list);
                        return;
                    } else {
                        ProVersionActivityLast.this.checkInAppPurchases();
                        return;
                    }
                }
                ProVersionActivityLast.this.checkInAppPurchases();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInAppPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast$$ExternalSyntheticLambda4
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityLast.this.m784x97745e13(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$checkInAppPurchases$4$hazem-nurmontage-videoquran-ProVersionActivityLast, reason: not valid java name */
    /* synthetic */ void m784x97745e13(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0) {
            this.hasPurchasedForever = false;
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Purchase purchase = (Purchase) it.next();
                if (purchase.getProducts().contains(PRODUCT_ID_FOREIVER) && purchase.getPurchaseState() == 1) {
                    this.hasPurchasedForever = true;
                    break;
                }
            }
        }
        if (!this.hasPurchasedForever) {
            handleNoPurchases();
            return;
        }
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.17
            @Override // java.lang.Runnable
            public void run() {
                ProVersionActivityLast.this.hideProgressFragment();
            }
        });
        BillingPreferences.saveSubscriptionStatus(getApplicationContext(), true);
        thnks();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSubscriptionPurchases(List<Purchase> list) {
        Iterator<Purchase> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getPurchaseState() == 1) {
                hideProgressFragment();
                BillingPreferences.saveSubscriptionStatus(getApplicationContext(), true);
                thnks();
                return;
            }
        }
        BillingPreferences.saveSubscriptionStatus(getApplicationContext(), false);
        checkInAppPurchases();
    }

    private void handleInAppPurchases(List<Purchase> list) {
        Iterator<Purchase> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getPurchaseState() == 1) {
                runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.18
                    @Override // java.lang.Runnable
                    public void run() {
                        ProVersionActivityLast.this.hideProgressFragment();
                    }
                });
                BillingPreferences.saveSubscriptionStatus(getApplicationContext(), true);
                thnks();
                return;
            }
        }
        handleNoPurchases();
    }

    private void handleNoPurchases() {
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.19
            @Override // java.lang.Runnable
            public void run() {
                ProVersionActivityLast.this.hideProgressFragment();
                Toast.makeText(ProVersionActivityLast.this.getApplicationContext(), ProVersionActivityLast.this.mResources.getString(R.string.not_have_susbcribe), 0).show();
            }
        });
        BillingPreferences.saveSubscriptionStatus(getApplicationContext(), false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        BillingClient billingClient = this.billingClient;
        if (billingClient == null || !billingClient.isReady()) {
            return;
        }
        this.billingClient.endConnection();
    }

    private void playVibration() {
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityLast.20
            @Override // java.lang.Runnable
            public void run() {
                new MyVibrationHelper(ProVersionActivityLast.this).vibrate(250L);
            }
        });
    }
}
