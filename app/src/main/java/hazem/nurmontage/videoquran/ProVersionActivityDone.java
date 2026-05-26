package hazem.nurmontage.videoquran;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.material.card.MaterialCardView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import hazem.nurmontage.videoquran.ProVersionActivityDone;
import hazem.nurmontage.videoquran.Utils.AppUtils;
import hazem.nurmontage.videoquran.Utils.BillingPreferences;
import hazem.nurmontage.videoquran.Utils.LocalPersistence;
import hazem.nurmontage.videoquran.Utils.LocaleHelper;
import hazem.nurmontage.videoquran.Utils.MyVibrationHelper;
import hazem.nurmontage.videoquran.Utils.ScreenUtils;
import hazem.nurmontage.videoquran.adabter.FeaturesAdabter;
import hazem.nurmontage.videoquran.adabter.ImgAdapter;
import hazem.nurmontage.videoquran.common.Common;
import hazem.nurmontage.videoquran.fragment.ProgressViewFragment;
import hazem.nurmontage.videoquran.model.ModelFeatures;
import hazem.nurmontage.videoquran.model.Template;
import hazem.nurmontage.videoquran.views.ButtonCustumFontBilling;
import hazem.nurmontage.videoquran.views.TextCustumFont;
import hazem.nurmontage.videoquran.views.TextCustumFontBold;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.xml.image.ImageUtil;

/* loaded from: classes2.dex */
public class ProVersionActivityDone extends Base implements PurchasesUpdatedListener {
    private static String PRODUCT_ID_FOREIVER = "sku.nurmontage.foreiver";
    private static String PRODUCT_ID_MONTH = "sku.nurmontage.month";
    private static String PRODUCT_ID_YEAR = "sku.nurmontage.year";
    private static final String VIDEO_ID = "DY76bAh7i3M";
    private BillingClient billingClient;
    private ButtonCustumFontBilling btnContinue;
    private MaterialCardView btn_foreiver;
    private Button btn_restore;
    private MaterialCardView btn_year;
    private Dialog dialog;
    private FeaturesAdabter featuresAdabter;
    boolean hasPurchasedForever;
    private boolean isBtnRestore;
    private boolean isClick;
    private ImageView ivForeiver;
    private ImageView ivYear;
    private Resources mResources;
    private Template mTemplate;
    private RecyclerView recyclerView;
    private TextCustumFont tvByMonth;
    private TextCustumFont tvNoCommitmentAr;
    private TextCustumFont tvNoCommitmentEn;
    private TextCustumFontBold tv_price_foreiver;
    private TextCustumFontBold tv_price_year;
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    private int color_select = -1;
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if (ProVersionActivityDone.this.mTemplate != null) {
                ProVersionActivityDone.this.toTrackAct();
            }
            ProVersionActivityDone.this.finish();
        }
    };
    private Map<String, ProductDetails> productDetailsMap = new HashMap();
    private String product_id_current = PRODUCT_ID_YEAR;
    private final Handler autoScrollHandler = new Handler(Looper.getMainLooper());
    private boolean isUserScrolling = false;
    private final Runnable autoScrollRunnable = new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.10
        @Override // java.lang.Runnable
        public void run() {
            LinearLayoutManager linearLayoutManager;
            try {
                if (ProVersionActivityDone.this.recyclerView == null || ProVersionActivityDone.this.isUserScrolling || (linearLayoutManager = (LinearLayoutManager) ProVersionActivityDone.this.recyclerView.getLayoutManager()) == null) {
                    return;
                }
                int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (findFirstVisibleItemPosition == -1) {
                    ProVersionActivityDone.this.autoScrollHandler.postDelayed(this, 16L);
                    return;
                }
                int itemCount = linearLayoutManager.getItemCount() / 3;
                if (findFirstVisibleItemPosition >= itemCount * 2) {
                    ProVersionActivityDone.this.recyclerView.scrollToPosition(findFirstVisibleItemPosition - itemCount);
                } else if (findFirstVisibleItemPosition < itemCount) {
                    ProVersionActivityDone.this.recyclerView.scrollToPosition(findFirstVisibleItemPosition + itemCount);
                } else {
                    ProVersionActivityDone.this.recyclerView.scrollBy(2, 0);
                }
                ProVersionActivityDone.this.autoScrollHandler.postDelayed(this, 16L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

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
        setContentView(R.layout.activity_pro_version_done);
        setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        setNavigationBarColor(ViewCompat.MEASURED_STATE_MASK);
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(false);
        insetsController.setAppearanceLightNavigationBars(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$$ExternalSyntheticLambda5
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ProVersionActivityDone.lambda$onCreate$0(view, windowInsetsCompat);
            }
        });
        Resources resources = getResources();
        this.mResources = resources;
        if (resources == null) {
            finish();
        }
        wakeLockAquire();
        ((TextView) findViewById(R.id.mtittle)).setText(this.mResources.getString(R.string.enjoy_all_premium_features_last));
        ((TextView) findViewById(R.id.hint_review)).setText(this.mResources.getString(R.string._4_8_434_reviews_28k_users));
        if (getIntent() != null && (stringExtra = getIntent().getStringExtra(Common.TEMPLATE)) != null) {
            this.mTemplate = (Template) LocalPersistence.readObjectFromFile(this, stringExtra);
        }
        findViewById(R.id.btn_on_back).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProVersionActivityDone.this.onBackPressedCallback.handleOnBackPressed();
            }
        });
        setupImg();
        thnks();
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    private void showFeatures() {
        try {
            Dialog dialog = new Dialog(this);
            this.dialog = dialog;
            dialog.setCancelable(true);
            this.dialog.requestWindowFeature(1);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog_premuim, (ViewGroup) null);
            this.dialog.setContentView(inflate);
            inflate.findViewById(R.id.dialog_title).setVisibility(8);
            inflate.findViewById(R.id.dialog_no).setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ProVersionActivityDone.this.cancelDialog();
                }
            });
            this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        cancelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        this.dialog = null;
    }

    private void help() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://chat.whatsapp.com/F0kqOjZS1VuBAvoiOG4XEZ"));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBtnHelp(boolean z) {
        findViewById(R.id.layout_help).setVisibility(0);
        Typeface createFromAsset = Typeface.createFromAsset(getResources().getAssets(), "fonts/ReadexPro_Medium.ttf");
        Button button = (Button) findViewById(R.id.btn_help);
        button.setTypeface(createFromAsset);
        button.setOnClickListener(new View.OnClickListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProVersionActivityDone.this.contact();
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
        String[] strArr = {"nurmontage.contact@gmail.com"};
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(null);
        ArrayList arrayList = new ArrayList();
        for (String str : this.mResources.getStringArray(R.array.feature_list)) {
            arrayList.add(new ModelFeatures(str));
        }
        FeaturesAdabter featuresAdabter = new FeaturesAdabter(arrayList);
        this.featuresAdabter = featuresAdabter;
        recyclerView.setAdapter(featuresAdabter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBillingConnection() {
        this.billingClient.startConnection(new BillingClientStateListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.5
            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == 0) {
                    ProVersionActivityDone.this.checkUserSubscriptionStatus();
                    ProVersionActivityDone.this.querySubscribe();
                    ProVersionActivityDone.this.queryPurchases();
                }
            }

            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingServiceDisconnected() {
                ProVersionActivityDone.this.startBillingConnection();
            }
        });
    }

    private void queryProducts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(PRODUCT_ID_FOREIVER).setProductType("inapp").build());
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new AnonymousClass6());
    }

    /* renamed from: hazem.nurmontage.videoquran.ProVersionActivityDone$6, reason: invalid class name */
    class AnonymousClass6 implements ProductDetailsResponseListener {
        AnonymousClass6() {
        }

        @Override // com.android.billingclient.api.ProductDetailsResponseListener
        public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
            if (billingResult.getResponseCode() != 0 || list == null) {
                return;
            }
            for (final ProductDetails productDetails : list) {
                ProVersionActivityDone.this.productDetailsMap.put(productDetails.getProductId(), productDetails);
                ProVersionActivityDone.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProVersionActivityDone.AnonymousClass6.this.m741xcb20e8c6(productDetails);
                    }
                });
            }
        }

        /* renamed from: lambda$onProductDetailsResponse$0$hazem-nurmontage-videoquran-ProVersionActivityDone$6, reason: not valid java name */
        /* synthetic */ void m741xcb20e8c6(ProductDetails productDetails) {
            ProVersionActivityDone.this.updateUI(productDetails);
        }
    }

    private void queryUserPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$$ExternalSyntheticLambda1
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityDone.this.m739x60a1e5a7(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$queryUserPurchases$1$hazem-nurmontage-videoquran-ProVersionActivityDone, reason: not valid java name */
    /* synthetic */ void m739x60a1e5a7(BillingResult billingResult, List list) {
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
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(arrayList).build(), new AnonymousClass7());
    }

    /* renamed from: hazem.nurmontage.videoquran.ProVersionActivityDone$7, reason: invalid class name */
    class AnonymousClass7 implements ProductDetailsResponseListener {
        AnonymousClass7() {
        }

        @Override // com.android.billingclient.api.ProductDetailsResponseListener
        public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
            if (billingResult.getResponseCode() != 0 || list == null) {
                return;
            }
            for (final ProductDetails productDetails : list) {
                ProVersionActivityDone.this.productDetailsMap.put(productDetails.getProductId(), productDetails);
                ProVersionActivityDone.this.runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$7$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProVersionActivityDone.AnonymousClass7.this.m742xcb20e8c7(productDetails);
                    }
                });
            }
        }

        /* renamed from: lambda$onProductDetailsResponse$0$hazem-nurmontage-videoquran-ProVersionActivityDone$7, reason: not valid java name */
        /* synthetic */ void m742xcb20e8c7(ProductDetails productDetails) {
            ProVersionActivityDone.this.updateUI(productDetails);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(ProductDetails productDetails) {
        if (productDetails.getProductType().equals("inapp")) {
            ProductDetails.OneTimePurchaseOfferDetails oneTimePurchaseOfferDetails = productDetails.getOneTimePurchaseOfferDetails();
            if (oneTimePurchaseOfferDetails != null) {
                this.tv_price_foreiver.setText(formatPriceWithSymbol(oneTimePurchaseOfferDetails.getPriceAmountMicros(), oneTimePurchaseOfferDetails.getPriceCurrencyCode()));
                return;
            } else {
                this.tv_price_foreiver.setText("N/A");
                return;
            }
        }
        if (productDetails.getProductType().equals("subs")) {
            ProductDetails.PricingPhase pricingPhase = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0);
            long priceAmountMicros = pricingPhase.getPriceAmountMicros();
            String priceCurrencyCode = pricingPhase.getPriceCurrencyCode();
            String productId = productDetails.getProductId();
            if (PRODUCT_ID_YEAR.equals(productId)) {
                this.tv_price_year.setText(formatPriceWithSymbol(priceAmountMicros, priceCurrencyCode));
                String formatPriceWithSymbol = formatPriceWithSymbol(Math.round(priceAmountMicros / 12.0d), priceCurrencyCode);
                StringBuilder sb = new StringBuilder();
                if (LocaleHelper.getLanguage(getApplicationContext()).equals("ar")) {
                    sb.append("فقط ").append(formatPriceWithSymbol).append(" /شهر");
                } else {
                    sb.append("Only ").append(formatPriceWithSymbol).append(" /month");
                }
                this.tvByMonth.setText(sb);
                return;
            }
            if (PRODUCT_ID_MONTH.equals(productId)) {
                this.tv_price_foreiver.setText(formatPriceWithSymbol(priceAmountMicros, priceCurrencyCode));
            }
        }
    }

    private String formatPriceWithSymbol(long j, String str) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);
        currencyInstance.setCurrency(Currency.getInstance(str));
        currencyInstance.setMaximumFractionDigits(2);
        currencyInstance.setMinimumFractionDigits(2);
        return currencyInstance.format(j / 1000000.0d);
    }

    private void updateUILast(ProductDetails productDetails) {
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
            if (PRODUCT_ID_YEAR.equals(productDetails.getProductId())) {
                this.tv_price_year.setText(priceStr != null ? priceStr : "N/A");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$$ExternalSyntheticLambda4
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityDone.this.m738x780026db(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$queryPurchases$2$hazem-nurmontage-videoquran-ProVersionActivityDone, reason: not valid java name */
    /* synthetic */ void m738x780026db(BillingResult billingResult, List list) {
        if (billingResult.getResponseCode() == 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                handlePurchase((Purchase) it.next());
            }
        }
    }

    private void showProgress() {
        findViewById(R.id.container_progress).setVisibility(0);
        if (isFinishing() || getSupportFragmentManager().isDestroyed()) {
            return;
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.container_progress, ProgressViewFragment.getInstance());
        beginTransaction.commit();
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

    private void launchPurchaseFlowINAPP(ProductDetails productDetails) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build());
        this.billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).build());
    }

    private void launchPurchaseFlowSUB(ProductDetails productDetails) {
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
            this.billingClient.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), new AcknowledgePurchaseResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$$ExternalSyntheticLambda2
                @Override // com.android.billingclient.api.AcknowledgePurchaseResponseListener
                public final void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    ProVersionActivityDone.lambda$handlePurchase$3(billingResult);
                }
            });
            BillingPreferences.saveSubscriptionStatus(getApplicationContext(), true);
            playVibration();
            thnks();
            return;
        }
        if (purchase.getPurchaseState() == 2) {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.8
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(ProVersionActivityDone.this.getApplicationContext(), "Purchase is pending", 0).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.9
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(ProVersionActivityDone.this.getApplicationContext(), "Purchase is in unknown state", 0).show();
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
        List<? extends Shape> m;
        Shape.DrawableShape loadDrawable = ImageUtil.loadDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart), true, true);
        KonfettiView konfettiView = (KonfettiView) findViewById(R.id.konfettiView);
        konfettiView.setVisibility(0);
        PartyFactory spread = new PartyFactory(new Emitter(3500L, TimeUnit.MILLISECONDS).max(100)).spread(Spread.ROUND);
        m = ProVersionActivity$$ExternalSyntheticBackport0.m(new Object[]{loadDrawable});
        konfettiView.start(spread.shapes(m).colors(Arrays.asList(-1216136524, -1124760279, -2019220, -1124760279)).setSpeedBetween(0.0f, 30.0f).position(new Position.Relative(0.5d, 0.3d)).build());
    }

    private void thnks() {
        ((TextCustumFont) findViewById(R.id.tv_thanks)).setText(this.mResources.getString(R.string.thanks_hint));
        initBtnHelp(true);
        explode();
    }

    /* renamed from: hazem.nurmontage.videoquran.ProVersionActivityDone$11, reason: invalid class name */
    class AnonymousClass11 implements Runnable {
        AnonymousClass11() {
        }

        @Override // java.lang.Runnable
        public void run() {
            int height = (int) (ProVersionActivityDone.this.recyclerView.getHeight() * 0.95f);
            if (height == 0) {
                height = (int) (ScreenUtils.getScreenHeight(ProVersionActivityDone.this) * 0.4f);
            }
            final ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(R.drawable.nur_2));
            arrayList.add(Integer.valueOf(R.drawable.nur_3));
            arrayList.add(Integer.valueOf(R.drawable.nur_4));
            arrayList.add(Integer.valueOf(R.drawable.nur_1));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(arrayList);
            arrayList2.addAll(arrayList);
            arrayList2.addAll(arrayList);
            ImgAdapter imgAdapter = new ImgAdapter(AppUtils.getAppVersionName(ProVersionActivityDone.this), arrayList2, height);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProVersionActivityDone.this, 0, false);
            linearLayoutManager.setItemPrefetchEnabled(true);
            linearLayoutManager.setInitialPrefetchItemCount(6);
            ProVersionActivityDone.this.recyclerView.setLayoutManager(linearLayoutManager);
            ProVersionActivityDone.this.recyclerView.setAdapter(imgAdapter);
            ProVersionActivityDone.this.recyclerView.setHasFixedSize(true);
            ProVersionActivityDone.this.recyclerView.setItemViewCacheSize(12);
            ProVersionActivityDone.this.recyclerView.setItemAnimator(null);
            ProVersionActivityDone.this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.11.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 1) {
                        ProVersionActivityDone.this.isUserScrolling = true;
                    } else if (i == 0) {
                        ProVersionActivityDone.this.isUserScrolling = false;
                        ProVersionActivityDone.this.startAutoScroll();
                    }
                }
            });
            ProVersionActivityDone.this.recyclerView.post(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$11$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ProVersionActivityDone.AnonymousClass11.this.m740x625f3f0e(arrayList);
                }
            });
            ProVersionActivityDone.this.startAutoScroll();
        }

        /* renamed from: lambda$run$0$hazem-nurmontage-videoquran-ProVersionActivityDone$11, reason: not valid java name */
        /* synthetic */ void m740x625f3f0e(List list) {
            ProVersionActivityDone.this.recyclerView.scrollToPosition(list.size());
        }
    }

    private void setupImg() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_img);
        this.recyclerView = recyclerView;
        recyclerView.post(new AnonymousClass11());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoScroll() {
        try {
            this.autoScrollHandler.removeCallbacks(this.autoScrollRunnable);
            this.autoScrollHandler.postDelayed(this.autoScrollRunnable, 250L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAutoScroll() {
        Runnable runnable;
        Handler handler = this.autoScrollHandler;
        if (handler == null || (runnable = this.autoScrollRunnable) == null) {
            return;
        }
        handler.removeCallbacks(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUserSubscriptionStatus() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("subs").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.12
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
                if (billingResult.getResponseCode() == 0) {
                    if (!list.isEmpty()) {
                        ProVersionActivityDone.this.handleSubscriptionPurchases(list);
                        return;
                    } else {
                        ProVersionActivityDone.this.checkInAppPurchases();
                        return;
                    }
                }
                ProVersionActivityDone.this.checkInAppPurchases();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInAppPurchases() {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), new PurchasesResponseListener() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone$$ExternalSyntheticLambda3
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                ProVersionActivityDone.this.m737x9770eeff(billingResult, list);
            }
        });
    }

    /* renamed from: lambda$checkInAppPurchases$4$hazem-nurmontage-videoquran-ProVersionActivityDone, reason: not valid java name */
    /* synthetic */ void m737x9770eeff(BillingResult billingResult, List list) {
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
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.13
            @Override // java.lang.Runnable
            public void run() {
                ProVersionActivityDone.this.hideProgressFragment();
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

    private void handleNoPurchases() {
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.14
            @Override // java.lang.Runnable
            public void run() {
                ProVersionActivityDone.this.hideProgressFragment();
                if (ProVersionActivityDone.this.isBtnRestore) {
                    Toast.makeText(ProVersionActivityDone.this.getApplicationContext(), ProVersionActivityDone.this.mResources.getString(R.string.not_have_susbcribe), 0).show();
                }
            }
        });
        BillingPreferences.saveSubscriptionStatus(getApplicationContext(), false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        stopAutoScroll();
        BillingClient billingClient = this.billingClient;
        if (billingClient == null || !billingClient.isReady()) {
            return;
        }
        this.billingClient.endConnection();
    }

    private void playVibration() {
        runOnUiThread(new Runnable() { // from class: hazem.nurmontage.videoquran.ProVersionActivityDone.15
            @Override // java.lang.Runnable
            public void run() {
                new MyVibrationHelper(ProVersionActivityDone.this).vibrate(250L);
            }
        });
    }
}
