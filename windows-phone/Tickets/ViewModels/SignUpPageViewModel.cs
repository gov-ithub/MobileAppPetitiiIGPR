using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class SignUpPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public SignUpPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void CreateAccount()
        {
            _navigationService.For<DashboardPageViewModel>().Navigate();
        }
    }
}
